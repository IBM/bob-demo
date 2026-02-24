# Install httpd 


**Mode:** DevOps CI

**Context:**

We want to generate a Continuous Integration pipeline for Tekton

**Prompt:**

```
Can you create a continuous integration pipeline using Tekton? The output image should be quay.io/lordofthejars/hello-world
```

**Result:**

Two directories containing a complete Tekton CI pipeline for building, containerizing, and scanning the hello-world Java application.
`k8s` directory with the Kubernetes Secrets configuration, and the `tekton` directory with all Tekton files.

---
