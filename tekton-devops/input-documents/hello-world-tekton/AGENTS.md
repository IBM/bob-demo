# Tekton Bob Skills  

## Skills (Agent Skills standard)
- Prefer using Skills over ad-hoc prompting; keep complexity in Skills.
- Skills live in `skills/`. Each skill is a folder with required `SKILL.md` (YAML frontmatter + Markdown).
- Use the skill by reading `SKILL.md` and following the instructions.

## Available Skills

To generate a Continuous Integration pipeline you need to run the following skills in the given order:

1. [secrets](skills/secrets/SKILL.md) - Generate the Kubernetes `Secret` and `ServiceAccount` to define the docker-registry data.
2. [build](skills/build/SKILL.md) - Generate a Tekton Task that clones a project, runs Maven to build the project, and uses buildh to build and push the container.
3. [security](skills/security/SKILL.md) - Generate Tekton Task that uses Trivy to scan generated container for vulnerabilities.
4. [pipeline](skills/pipeline/SKILL.md) — Generate Tekton Pipeline that defines the workflow of the task execution.

