## Skill: Create an AI Service 

### Purpose
Enable Bob to create a Quarkus LangChain4j AI Service

### Overview
Quarkus creates an interface as a LangChain4j interface annotated with `@io.quarkiverse.langchain4j.RegisterAiService` and single method.

### Core Rules
1. Create the interface with the provided name by the user. If no name then name it **Assistant**.
2. Annotate the interface with `@RegisterAiService`.
3. Create a single method in the interface named **chat** which receives a `String` and returns a `String`.


### Example Resource

```java
@RegisterAiService
public interface Assistant {
    String chat(String msg);
}
```

### Notes
- Use `@RegisterAiService` annotation and remember to add the import of the annotation `@io.quarkiverse.langchain4j.RegisterAiService`.

### Validation Check
After adding the annotation, Bob should:
- Verify that the interface is annotated with `@RegisterAiService`.
