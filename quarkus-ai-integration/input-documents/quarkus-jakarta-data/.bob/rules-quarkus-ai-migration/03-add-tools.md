## Skill: Annotate a Java method as a LangChain4j Tool

### Purpose
Enable Bob to annotate with `@dev.langchain4j.agent.tool.Tool` to given method to become a LangChain4j tool.

### Overview
Quarkus annotates a method with LangChain4j `@Tool` providing some description to the method as well. 

### Core Rules
1. Always use the `@dev.langchain4j.agent.tool.Tool` to the given method.
2. You need to read the method code, get a description of what the code does, and put as @Tool annotation value this description. 
3. Find the interface annotated with `@RegisterAiService` and annotate the method with `io.quarkiverse.langchain4j.ToolBox` passing as value the class provided by user containing the `@Tool` method.
4. The `@Toolbox` annotation receives as value a `Class`.
5. If the user doesn't provide the class name and the method name, just stop the execution and asking for the user to provide this information.

### Example Resource

```java
public class HotelSearch {
    @Tool("Finds hotels for the given location")
    public String findHotels(String location) {

    }
}

@RegisterAiService
public class Assistant {
    @Toolbox(HotelSearch.class)
    String chat(String chat);
}
```

### Notes
- Use `@Tool` annotation and remember to add the import of the annotation `dev.langchain4j.agent.tool.Tool`.
- Use `@Toolbox` annotation and remember to add the import of the annotation `io.quarkiverse.langchain4j.ToolBox`.

### Validation Check
After adding the annotation, Bob should:
- Verify that the class and method provided by the user is annotated with `@Tool`.
- Verify that the AI Service interface has the method annotated with `@Toolbox` and the class as value.
