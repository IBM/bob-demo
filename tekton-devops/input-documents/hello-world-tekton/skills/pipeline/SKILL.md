---
name: pipeline
description: Generate Tekton Pipeline that defines the workflow of the task execution.
metadata:
  version: "0.1"
  author: "Alex Soto"
  standard: "agentskills.io"
---

# Continuous Integration Pipeline

Generate Tekton Pipeline that defines the workflow of the task execution.
The pipline should use the tasks generated previously in the pipeline.

## Instructions for Bob

### Step 1: Verify that user set all required parameters 

For this task you can get the git repository from the `.git/config` file in the origin field.
Find within the project a `Dockerfile` location.

The user must provide the image name, and if not ask about it.

### Step 2: Generate a Tekton Pipeline

Generate a Tekton `Pipeline` that calls previous tasks, first the build and then the security

The Pipeline `taskRef` should match the names set in the previous tasks.
The input parameters of each task sholud be pipeline parameters too, and create and set the required workspaces used by the tasks.

### Step 3: Generate a Tekton PipelineRun

Generate a `PipelineRun` to run the generated Pipeline, but the `serviceAccountName` should be set to the service account created.

## Critical
- `Pipeline` should contain all the parameters defined in the tasks.
- Manage Tekton workspaces correctly
- `PipelineRun` should set the serviceAccount to the one defining the Secret to access the container registry

