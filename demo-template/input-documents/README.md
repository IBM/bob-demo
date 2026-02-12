# Input Documents

This folder contains example input files that IBM Bob processes during the demo.

## Purpose

Input documents help others:
- Understand the starting point of the demo
- Reproduce the demo with similar inputs
- See what types of files Bob can work with
- Learn how to structure their own input materials

## What to Include

### Source Files
- Code files that Bob will analyze or modify
- Configuration files
- Data files (JSON, YAML, CSV, etc.)
- Documentation files

### Context Files
- README files explaining the input
- Requirements documents
- Specifications or design documents
- Any reference materials used

## Organization Tips

- Use descriptive folder names if you have multiple input categories
- Include a brief description of each file's purpose
- Maintain the original file structure if it's relevant to the demo
- Add comments in code files to highlight important sections

## Example Structure

```
Input Documents/
├── README.md (this file)
├── src/
│   ├── app.js (main application file)
│   └── utils.js (utility functions)
├── config/
│   └── settings.json (configuration)
└── docs/
    └── requirements.md (project requirements)
```

## File Descriptions

When adding input files, consider including a table here:

| File | Purpose | Notes |
|------|---------|-------|
| `example.js` | Sample JavaScript file | Contains legacy code to be modernized |
| `config.json` | Configuration file | Settings that Bob will reference |