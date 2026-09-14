# Prompt: Debug Weather Forecast Tool Chain

**Prompt:**

```
Use the weather MCP server to get the forecast for San Francisco
```

**Result:**

Bob executed the MCP query and traced the full tool chain. When it detects error, it automatically switches the mode to 🔍 MCP Debugger. It detected that the `get_forecast` tool call succeeded in reaching the MCP server, but the server's upstream HTTP request to the weather API timed out after 5 seconds due to a missing `timeout` configuration on the axios client.

Bob generated `mcp-debug-report-2026-02-26T04-26-00Z.md` containing:
- An ASCII execution flowchart showing 4 steps, with ❌ at step [4]
- Full request/response JSON at the failure point
- Root cause: no `timeout` set on the axios instance
- Backend fix: add `timeout: 4000` and connection pool settings to `axios.create()`
- Verification steps to confirm the fix works

---

## Follow-up Prompt (after applying the fix)

**Prompt:**

```
Use the weather MCP server to get the forecast for San Francisco
```
