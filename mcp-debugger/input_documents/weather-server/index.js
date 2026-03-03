#!/usr/bin/env node

/**
 * Weather MCP Server
 * 
 * A simple MCP server that provides weather forecast data.
 * 
 * BUG: The axios HTTP client has no timeout configured, causing
 * connection timeouts when the upstream weather API is slow or unresponsive.
 */

import { Server } from '@modelcontextprotocol/sdk/server/index.js';
import { StdioServerTransport } from '@modelcontextprotocol/sdk/server/stdio.js';
import axios from 'axios';

const WEATHER_API_URL = 'https://api.weather.example.com/v1/forecast';

// BUG: No timeout or connection pool configuration
// This will cause the server to hang indefinitely when the API is slow
const httpClient = axios.create({
  baseURL: WEATHER_API_URL,
  // Missing: timeout, maxRedirects, httpAgent, httpsAgent
});

// Create MCP server instance
const server = new Server(
  {
    name: 'weather-server',
    version: '1.0.0',
  },
  {
    capabilities: {
      tools: {},
    },
  }
);

// Register tools/list handler
server.setRequestHandler('tools/list', async () => {
  return {
    tools: [
      {
        name: 'get_forecast',
        description: 'Get weather forecast for a specific location',
        inputSchema: {
          type: 'object',
          properties: {
            location: {
              type: 'string',
              description: 'City name or coordinates (e.g., "San Francisco" or "37.7749,-122.4194")',
            },
            units: {
              type: 'string',
              enum: ['metric', 'imperial'],
              description: 'Temperature units (metric for Celsius, imperial for Fahrenheit)',
              default: 'metric',
            },
          },
          required: ['location'],
        },
      },
    ],
  };
});

// Register tools/call handler
server.setRequestHandler('tools/call', async (request) => {
  const { name, arguments: args } = request.params;

  if (name === 'get_forecast') {
    const { location, units = 'metric' } = args;

    try {
      // BUG: This request has no timeout
      // If the upstream API is slow, this will hang until the MCP client times out
      const response = await httpClient.get('', {
        params: {
          location,
          units,
          appid: 'demo-key',
        },
      });

      const forecast = formatForecast(response.data, units);

      return {
        content: [
          {
            type: 'text',
            text: forecast,
          },
        ],
      };
    } catch (error) {
      // Error handling
      return {
        content: [
          {
            type: 'text',
            text: `Error fetching weather data: ${error.message}`,
          },
        ],
        isError: true,
      };
    }
  }

  throw new Error(`Unknown tool: ${name}`);
});

/**
 * Format weather data into a readable string
 */
function formatForecast(data, units) {
  const tempUnit = units === 'metric' ? '°C' : '°F';
  
  return `Weather Forecast for ${data.location}:
Temperature: ${data.temp}${tempUnit}
Feels Like: ${data.feels_like}${tempUnit}
Condition: ${data.condition}
Description: ${data.description}
Humidity: ${data.humidity}%
Wind Speed: ${data.wind_speed} ${units === 'metric' ? 'm/s' : 'mph'}
Pressure: ${data.pressure} hPa`;
}

/**
 * Main entry point
 */
async function main() {
  const transport = new StdioServerTransport();
  await server.connect(transport);
  
  console.error('Weather MCP Server running on stdio');
}

main().catch((error) => {
  console.error('Fatal error:', error);
  process.exit(1);
});

// Made with Bob
