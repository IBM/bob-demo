# Use Beads for Issue Tracking

This project uses **Beads** - an AI-native, git-integrated issue tracking system that lives directly in the codebase.

## Core Principle

**Always use Beads CLI (`bd`) for issue management** instead of creating separate tracking documents or TODO files. Issues should be tracked in the Beads system, not in markdown files or comments.

## Essential Commands

### Creating Issues
```bash
# Create a new issue
bd create "Issue title and description"

# Create with specific status
bd create "Issue title" --status in_progress
```

### Viewing Issues
```bash
# List all issues
bd list

# Show specific issue details
bd show <issue-id>

# Filter by status
bd list --status open
bd list --status in_progress
bd list --status done
```

### Updating Issues
```bash
# Update issue status
bd update <issue-id> --status in_progress
bd update <issue-id> --status done

# Add comments or details
bd update <issue-id> --comment "Additional context"
```

### Syncing
```bash
# Sync with git remote (run after commits)
bd sync
```

## When to Use Beads

✅ **DO use Beads for:**
- Bug reports and tracking
- Feature requests
- Task planning and decomposition
- Technical debt items
- Documentation needs
- Any work that needs tracking

❌ **DON'T create:**
- Separate TODO.md files
- Issue tracking in docs/
- Long-term planning documents that duplicate issue content

## Integration with Development Workflow

1. **Before starting work**: Check `bd list` for open issues
2. **When planning**: Create issues with `bd create` for each task
3. **During development**: Update status with `bd update <issue-id> --status in_progress`
4. **After completion**: Mark done with `bd update <issue-id> --status done`
5. **With commits**: Run `bd sync` to keep issues synchronized

## AI Agent Guidelines

When working as an AI coding assistant:
- Check existing issues with `bd list` before creating new tracking documents
- Reference issue IDs in commit messages (e.g., "fix: resolve authentication bug (#123)")
- Update issue status as work progresses
- Create new issues for discovered bugs or needed improvements
- Use `bd show <issue-id>` to get full context on tasks

## Benefits

- **Git-native**: Issues sync with code commits
- **AI-friendly**: CLI-first design works seamlessly with AI agents
- **Always available**: Works offline, no web UI required
- **Context-aware**: Issues live next to the code they reference
- **Fast**: Lightweight and stays out of your way

## Learn More

- Documentation: [github.com/steveyegge/beads](https://github.com/steveyegge/beads)
- Quick start: Run `bd quickstart`
- Local README: `.beads/README.md`