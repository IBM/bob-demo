# MCP Debugger

This demo showcases the **🔍 MCP Debugger** custom Bob mode — a specialized mode for debugging MCP Server tool chains. It executes MCP queries, monitors the complete tool execution chain, and generates structured diagnostic reports with visual flowcharts and actionable fixes.

### Prerequisites

1. IBM Bob installed and running
2. An MCP server you want to debug (or use the example weather server in `input-documents/`)

  ### What Bob Does

  1. Executes your MCP query using the configured MCP server
  2. Tracks every tool call in the execution chain with timing
  3. Captures request/response payloads at each step
  4. Identifies the exact failure point
  5. Generates a `mcp-debug-report-{timestamp}.md` file with:
    - Visual ASCII execution flowchart
    - Failure details (tool, error, timestamp, payloads)
    - Root cause analysis
    - Specific backend fix with code snippets
    - Verification steps

  6. If Auto-debug is set, Bob will automatically switch to the MCP Debugger mode when a failure is detected. 
---

## Step-by-Step Process

### Step 1: Install the Custom Mode

Copy the `.bob/custom_modes.yaml` and `.bob/custom_instructions.md` directory into your project root. This registers the **🔍 MCP Debugger** mode in Bob.

```
your-project/
└── .bob/
    ├── custom_modes.yaml   ← mode definition
    ├── custom_instructions.md  ← instructions for the mode
    └── mcp.json            ← MCP server connection config for your mcp server
```

### Step 2: Configure Your MCP Server

Edit `.bob/mcp.json` to point to the MCP server you want to debug. Example for a local weather server:

```json
{
  "mcpServers": {
    "weather": {
      "type": "stdio",
      "command": "node",
      "args": ["./weather-server/index.js"],
      "disabled": false
    }
  }
}
```

### Step 3: Switch to MCP Debugger Mode or use Auto-debug configuration

In Bob's mode selector, choose **🔍 MCP Debugger**.

## Auto-Debug Configuration

Bob can automatically switch to MCP Debugger mode when detecting failures. The custom instructions in `.bob/custom_instructions.md` configure Bob to:

1. **Auto-detect MCP failures**: Tool errors, timeouts, connection issues, invalid parameters
2. **Automatically switch** to `mcp-debugger` mode using the `switch_mode` tool
3. **Re-execute** the failed operation with full logging enabled
4. **Generate comprehensive report** with execution flowchart and actionable fixes
5. **Save report** as `mcp-debug-report-{timestamp}.md`

This means you don't need to manually switch modes - Bob will automatically enter debug mode when it encounters MCP issues and provide detailed diagnostic reports.

### Step 4: Submit Your Debug Query if not auto-debug is not enabled

Use a prompt like:

```
Use the weather MCP server to get the forecast for San Francisco
```

### Step 5: Review the Debug Report

Bob generates a `mcp-debug-report-{timestamp}.md` file. See `optional-generated-content/` for an example report.

---

## Key Takeaways

- The MCP Debugger mode makes invisible tool chain failures visible through structured flowcharts
- Timing data pinpoints performance bottlenecks vs. hard failures
- Reports are intentionally short and scannable — focused on the fix, not verbose logs


## Tips for MCP Debugger Prompts

1. **Be direct** — just describe the MCP query you want to run, as if you were using the tool normally. Bob handles the debugging automatically.
2. **Name the MCP server** — e.g., "Use the weather MCP server to..." so Bob knows which server to target.
3. **Describe expected behavior** — helps Bob identify what "success" looks like vs. the actual failure.
4. **No need to ask for a report** — the mode's custom instructions automatically generate a structured debug report after every execution.
5. **Follow up after fixing** — re-run the same prompt after applying the fix to verify the flowchart shows all ✅ steps.

