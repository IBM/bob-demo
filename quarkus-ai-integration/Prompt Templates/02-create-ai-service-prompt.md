# Creates an AI Service

**Mode:** Quarkus Developer

**Context:**

Asks Bob to create an AI Service to send user-provided questions.

**Prompt:**

```
Create an AI Service for this application.
```

**Result:**

* Bob creates an AI Service interface with methods tailored to the application.
* Creates a REST API Resource class that injects the AI Service and provides an API for external use.
* Scaffolds an `application.properties` file with common configuration properties set to dummy values.

**Follow-up Actions:**

- Implements function calling for the legacy logic so the model can invoke it.

---
