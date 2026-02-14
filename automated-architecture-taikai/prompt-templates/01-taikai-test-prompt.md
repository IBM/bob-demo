# Create a Taikai test 

**Mode:** Advanced

**Context:**

Create a Taikai test in Java Spring Boot PetClinic to enforce past architectural decissions.
This is important because helps on knowledge transfer between teams as a document might become outdated or not fully read, while a test passes or not.

**Prompt:**

```
This is a Spring Boot application, can you generate a junit test using enofex/taikai project to enforce the architectural constraints following the current design of the application, not generic ones. Analyze the project and then generate the test.Consult documentation from the project @https://github.com/enofex/taikai/blob/main/docs/documentation.md  to generate correct and updated code.
```

**Result:**

Bob creates two files, one the JUnit Test itself with the architecture description in the test, and also a `markdown` file with the explanation of the guidelines followed to detect the architecture decissions.

**Follow-up Actions:**

- Verify that the markdown matches with what you can see on the Spring Boot Pet Clinic code.

---
