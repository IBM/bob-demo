# Create a Taikai test 

**Mode:** Advanced

**Context:**

Create a Taikai test in Java Spring Boot PetClinic to enforce past architectural decisions.
This is important because it helps with knowledge transfer between teams: a document might become outdated or not be fully read, while a test passes or fails, making the change more obvious.

**Prompt:**

```
This is a Spring Boot application, can you generate a junit test using enofex/taikai project to enforce the architectural constraints following the current design of the application, not generic ones. Analyze the project and then generate the test.Consult documentation from the project @https://github.com/enofex/taikai/blob/main/docs/documentation.md  to generate correct and updated code.
```

> [!IMPORTANT]
> When Bob modifies the `pom.xml` file to register the Taikai library, modify manually (if necessary) to the version of Taikai to 1.51.0

**Result:**

Bob creates two files: one is the JUnit Test itself with the architecture description in the test, and the other is a `markdown` file with the explanation of the guidelines followed to detect the architecture decisions.

**Follow-up Actions:**

- Verify that the markdown matches what you can see on the Spring Boot Pet Clinic code.

---
