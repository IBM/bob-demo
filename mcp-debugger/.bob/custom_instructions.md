# Bob Custom Instructions - MCP Auto-Debug

## Auto-Switch on MCP Failures

When you detect any MCP failure (tool errors, timeouts, connection issues):

1. **Immediately use `switch_mode`** to switch to `mcp-debugger` mode
2. **Re-execute** the failed operation to capture full logs
3. **Generate debug report** with:
   - Execution flowchart (mandatory)
   - Failure details with payloads
   - Root cause analysis
   - Backend fixes with code
   - Verification steps
4. **Save as** `mcp-debug-report-{timestamp}.md`

## Trigger Patterns

Auto-switch when you see:
- "MCP tool execution failed"
- "Tool 'xyz' returned error"
- Connection/timeout errors
- Invalid parameter errors
- Any MCP-related exception

## Example

```
[Detect MCP error] → switch_mode(mcp-debugger) → Re-run with logging → Generate report → Present findings