# Implementation Journey: [Quarkus AI Integration]

**Date:** [02/11/2026]  
**Duration:** 10 min 
**Mode(s) Used:** Custom *Quarkus Developer* mode

## Initial Goal

Migrate a Quarkus Application from exposing the logic as a REST API to a chatbot-like application using LangChain4j.

---

## Step-by-Step Process

### Step 1: Start Quarkus MCP Server

**What I did:** 

Go to a terminal window, and inside `Input Documents/quarkus-jakarta-data` run the following command: `./mvnw quarkus:dev`

This command will start the Quarkus application in dev mode with the MCP Server enabled.
Then open IBM Bob IDE and open the `Input Documents/quarkus-jakarta-data` project.

**Bob's response:**

Verify that Bob is connected to the Quarkus MCP Server by inspecting the MCP tab:

![Show MCP Servers in IBM Bob](/quarkus-ai-integration/Screenshots/bob1.png)

**Outcome:**

IBM Bob connected to the Quarkus MCP Server.

### Step 2: Select Quarkus Developer

**What I did:**

To use the Quarkus Developer mode to migrate projects to AI, we created a custom Bob mode with rules to provide guidance on how to integrate with AI.

**Bob's response:** 

Go to the bottom-left dropdown menu and select _Quarkus Developer_ mode.

**Outcome:**

![IBM Bob Mode](/quarkus-ai-integration/Screenshots/bob2.png)

### Step 3: Time for Prompting

**What I did:** 

At this point, we can start prompting Bob, check the `Prompt Templates` folder for the prompting.

---

## Key Decisions

### Decision 1: Create a Custom Mode with MCP

**Context:**

Bob needs to add the correct dependencies/extensions in the Quarkus project to use LangChain4j.

**Options Considered:** 

Bob could modify the `pom.xml` file directly, adding the dependencies.

**Choice Made:**

Implement a custom mode connecting to the Quarkus MCP Server.

**Rationale:** 

Quarkus provides an MCP Server to find the currently installed extensions, list all available extensions, and set the extension to the correct version.
In this way, we make Quarkus decide which version to use in each case.

### Decision 2: Create Rules for the integration

**Context:** 

Bob has the knowledge to code in Quarkus and to provide LangChain4j integration, but in most cases, you want Bob to produce code that follows defined patterns.

**Options Considered:** 

Let Bob produce the code without any guidelines.

**Choice Made:** 

Create specific rules for the mode.

**Rationale:**

Even though Bob could generate code by its own, we decided to provide some guidelines, as this is more of a realistic scenario.

---

### Challenge 1: Hallucinations with wide context

**Issue:** 

If the prompt was too generic, like: _Transform this application into an AI application_, the solution produced by Bob could be far from perfect, hallucinating or guessing too much.  

**Solution:** 

Implement all integrations with multiple prompts, executing each in turn.

**Learning:**

Always better to have multiple prompts rather than one containing all the information.

---

## Final Outcome

**What was achieved:**
- Quarkus application integrated with LangChain4j
- AI Service generation
- Tools creation
- Endpoint for receiving chat messages

**Quality assessment:**

Overall, it works really well at first and produces a high-quality response.

**Next steps:**

We could also implement an RAG part.

---

## Recommendations

**For similar demos:**

- https://www.youtube.com/channel/UC-dkbPjzN2bh-k-V4rZQppQ
