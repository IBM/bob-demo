# Weather MCP Server (with Bug)

A simple MCP server that provides weather forecast data. This server intentionally contains a bug to demonstrate the MCP Debugger mode.

## The Bug

The `httpClient` axios instance on **line 20** has no `timeout` configuration:

```javascript
const httpClient = axios.create({
  baseURL: WEATHER_API_URL,
  // Missing: timeout, maxRedirects, httpAgent, httpsAgent
});
```

### What Happens

When the upstream weather API (`https://api.weather.example.com/v1/forecast`) is slow or unresponsive:
1. The `httpClient.get()` call on line 78 hangs indefinitely
2. The MCP client waits for a response
3. After 5 seconds, the MCP client times out with `ETIMEDOUT`
4. The server process continues waiting, exhausting connection pool resources

### Expected Behavior

The server should timeout the HTTP request before the MCP client times out, allowing for proper error handling and retry logic.

## Installation

```bash
npm install
```

## Running the Server

```bash
npm start
```

Or directly:
```bash
node index.js
```

## MCP Configuration

Add this to your `.bob/mcp.json`:

```json
{
  "mcpServers": {
    "weather": {
      "type": "stdio",
      "command": "node",
      "args": ["./input_documents/weather-server/index.js"],
      "disabled": false
    }
  }
}
```

## Testing with MCP Debugger

1. Switch Bob to **🔍 MCP Debugger** mode
2. Run: `Use the weather MCP server to get the forecast for San Francisco`
3. Bob will trace the tool chain and identify the timeout bug
4. Bob will generate a debug report with the fix

## The Fix

Bob will suggest adding timeout and connection pool configuration:

```javascript
const httpClient = axios.create({
  baseURL: WEATHER_API_URL,
  timeout: 4000,  // 4s — safely under the MCP client's 5s limit
  maxRedirects: 3,
  httpAgent: new http.Agent({ keepAlive: true, maxSockets: 10 }),
  httpsAgent: new https.Agent({ keepAlive: true, maxSockets: 10 }),
});