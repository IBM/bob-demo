## Skill: Register Quarkus LangChain4j Extension

### Purpose
Enable Bob to register the **Quarkus LangChain4j extension** for the required model .

### Overview
Quarkus provides an extension to integrate with LangChain4j.

ONLY register an extension, DON'T try to generate any resource.

### Core Rules
1. Always use the Quarkus MCP server to list the available extensions.
2. Always use the Quarkus MCP server to add the required LangChain4j extension depending on the model.
3. The extension should be the integration of LangChain4j with the required model by the user 
4. Don't generate any source file, only register the dependency. 

### Example Resource

The user question is: I want to add OpenAI support in my application.

Using the Quarkus MCP Server you should add the `quarkus-langchain4j-openai` dependency.

### Notes
- Avoid add dependencies of models not requested by user.
- Don't Generate any source file, your mission is only to register the extension.

### Validation Check
After registering the extension, Bob should:
- Verify that the extension has been registered with the required model.
