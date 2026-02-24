---
name: secrets
description: Generate the Kubernetes `Secret` and `ServiceAccount` to define the docker-registry data.
metadata:
  version: "0.1"
  author: "Alex Soto"
  standard: "agentskills.io"
---

# Kubernetes Secrets

Generate the Kubernetes `Secret` and `ServiceAccount` to define the docker-registry data.

## Instructions for Bob

### Step 1: Verify that user set all security parameters 

If the user has not told you the container registry host, and the user and password to authenticate to Contaienr registry.

### Step 2: Generate a docker registry Secret

Creates a Kubernetes `Secret` of type docker registry manifest with the provided container registry host.
The data is a docker config json file in base64 that you should create with the provided data.

The following snippet shows an example:

```yaml
apiVersion: v1
kind: Secret
metadata:
  name: registry-credentials
  annotations:
    tekton.dev/docker-0: https://index.docker.io/v1/
type: kubernetes.io/dockerconfigjson
data:
  .dockerconfigjson: <BASE64_DOCKER_CONFIG_JSON>
```

Then create a Kubernetes `SecretAccount` file referring to the secret created above:

```yaml
apiVersion: v1
kind: ServiceAccount
metadata:
  name: build-bot
secrets:
  - name: registry-credentials
```

## Critical
- Always generated the manifests in different files.
- Create a valid docker config json file and encode it in base64 as data.