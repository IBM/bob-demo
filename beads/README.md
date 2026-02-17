# Implementation Journey: [Setting Up Beads Locally for Use with IBM Bob]

This demo shows how to set up Beads, a distributed, git-backed graph issue tracker for AI agents, to use with Bob.


**Date added:** [02/17/2026]  
**Duration:** 10 min 
**Mode(s) Used:** Advanced

## Initial Goal

This guide documents the process for installing [Beads](https://github.com/steveyegge/beads#readme), initializing it in your project, and integrating it with IBM Bob.

---

## Step-by-Step Process

### Step 1: Install Beads

Install the `bd` CLI using Homebrew:

```bash
brew install beads
```

For other installation options, see the [Beads installation guide](https://github.com/steveyegge/beads/blob/main/README.md#-installation).

**Outcome:**

`bd` CLI is available on your system.

### Step 2: Initialize Beads in the Project

From inside the project directory:

```bash
bd init --stealth
```

What this does:
- Creates the `.beads/` directory for Beads' local data
- Initializes storage (SQLite database + JSONL issues)
- `--stealth` keeps Beads artifacts out of version control

**Bob's response:**
n/a

**Outcome:**

`.beads/` directory is created and Beads is initialized in stealth mode.

### Step 3: Integrate Beads With Bob

To preserve stealth mode, avoid running `bd setup` commands, which would generate config files inside the project.

Instead:
1. Ask Bob to generate a rule describing how it should use Beads for issue tracking
2. Refine the rule by referencing the Beads guidelines in the `.beads/` directory
3. Add the rule to Bob's configuration (`.bob/rules/beads-project.md`), not to project files

This makes Bob aware of Beads without modifying tracked files.

See [prompt-templates/generate-beads-rule-prompt.md](prompt-templates/generate-beads-rule-prompt.md) for an example prompt, and [optional-generated-content/beads-project.md](optional-generated-content/beads-project.md) for a sample generated rule.

**Bob's response:**
n/a

**Outcome:**

Bob has a rule for using Beads; no tracked project files are modified.

### Step 4: Set Up Beads MCP Server for Bob

Bob's plan mode cannot run shell commands, so it needs the Beads MCP server to access Beads functionality.

Install the MCP server:

```bash
pipx install beads-mcp
```

This installs the `beads-mcp` executable, which exposes Beads operations through the Model Context Protocol (MCP).

Configure Bob to use the MCP server by updating `.bob/mcp.json` ([example](optional-generated-content/mcp.json)):

```json
{
  "mcpServers": {
    "beads": {
      "command": "beads-mcp"
    }
  }
}
```

This allows Bob to launch `beads-mcp` and use Beads tools via MCP instead of shell commands.

**Bob's response:**
n/a

**Outcome:**

Bob can use Beads via MCP in plan mode.

### Step 5: Install the VS Code Extension (Optional)

Install the [Beads extension](https://github.com/jdillon/vscode-beads) from the VS Code Marketplace for visualizing Beads issues in your editor:

1. Open VS Code Extensions view (`Cmd+Shift+X` or `Ctrl+Shift+X`)
2. Search for "Beads"
3. Click Install

Requirements:
- `bd` on your `PATH`
- Project initialized with `bd init`

The extension reads from the `.beads` directory and surfaces issues inside VS Code.

**Bob's response:**
n/a

**Outcome:**

Beads issues are visible in VS Code (optional).

---

## Final Outcome

**What was achieved:**
- Beads is installed and initialized in the project (stealth mode).
- Bob has a rule and MCP server configuration to use Beads for issue tracking and planning.
- Optional: VS Code extension for visualizing Beads issues.

**Optional next steps:**
- **Auto-approve MCP calls** - Add specific Beads tools to Bob's MCP auto-approval settings to skip prompts.
- **Auto-approve `bd` commands** - Configure command auto-approval in Bob settings so Bob can run `bd` in code mode without confirmation.
- **Temporarily disable Beads** - Rename `.bob/rules/beads-project.md` (e.g., add `.disabled`) to stop Bob from using Beads; rename back to re-enable.
