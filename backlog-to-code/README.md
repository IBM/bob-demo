# Implementation Journey: [Java Backlog-to-Code with MCP]

This demo shows how Bob can bridge the gap between project management and technical implementation. By using a **Backlog MCP**, Bob reads a `Backlog.md` file, implements a Java class following the **BCE (Boundary-Control-Entity)** pattern, and automatically updates the task status.

**Date added:** [02/17/2026]  
**Duration:** 8 min  
**Mode(s) Used:** Advanced, Quarkus Mode

## Initial Goal

Automate the transition from a markdown-based backlog task to a verified, architecturally sound Java implementation.

---

## Step-by-Step Process

### Step 0: Prerequesites
* Install the Quarkus CLI (https://quarkus.io/guides/cli-tooling)
* Install backlog.md (https://github.com/MrLesk/Backlog.md)
* Install git 


### Step 1: Initialize the Project and Backlog

Go to a terminal window and create a new project with the quarkus CLI. We will initialize a git repository and a basic `Backlog.md` file to act as our "Source of Truth" for tasks.

```bash
quarkus create app my-quarkus-app
cd app my-quarkus-app
```
Initialize a git locally:

```bash
git init
```

Initialize Backlog locally and follow the configuration steps. 

```bash
backlog init my-quarkus-app
```

Create a new Task to be implemented by Bob.

```bash
backlog task create "Implement StockControl Boundary using Panache"
```

Create new single task steps for the implementation:

```bash
backlog task edit 1 --ac "add quarkus-rest, quarkus-hibernate-orm-panache, quarkus-jdbc-h2, hibernate-validator"
backlog task edit 1 --ac "Create Stock entity extending PanacheEntity with fields: name, quantity, price"
backlog task edit 1 --ac "Add validation annotations (@NotBlank, @NotNull, @Positive)"
backlog task edit 1 --ac "Create StockControl service with @ApplicationScoped and @Transactional methods"
backlog task edit 1 --ac "Implement CRUD operations using Panache static methods (persist(), findById(), listAll(), deleteById())"
backlog task edit 1 --ac "Create StockResource REST endpoint with @Path("/api/stocks")"
backlog task edit 1 --ac "Inject StockControl and map HTTP methods (GET, POST, PUT, DELETE)"
backlog task edit 1 --ac "Configure H2 datasource in application.properties"
backlog task edit 1 --ac "Create StockResourceTest with @QuarkusTest"
backlog task edit 1 --ac "Test all endpoints with RestAssured"
backlog task edit 1 --ac "Verify tests ./mvnw verify"
```

**Outcome:**
A local project is ready with a pending task in the backlog.

### Step 2: Configure the Backlog MCP

Bob needs a "bridge" to interact with the backlog server. We define this in a local configuration file as MCP Server that Bob can use.

**Action:**
Create a `.bob/mcp.json` file with the following content:

```json
{
    "mcpServers": {
        "backlog": {
            "command": "backlog",
            "args": [
                "mcp",
                "start"
            ]
        }
    }
}
```

**Outcome:**
The Backlog MCP server is registered locally for this project.

---

### Step 3: Start Bob in the newly created folder and check the MCP server connection

Go to Bob Settings and select MCP. The backlog mcp card should have a green dot now.


## Challenge 1: Context Awareness (Finding the Task)

**Issue:** Usually, a developer has to copy-paste requirements from a ticket into the AI prompt, leading to "context drift" or missed details.

**Solution:** Use the Backlog MCP to let Bob "see" the pending work directly.

**Prompt:**

> "What is the next pending task in my backlog?"

**Outcome:**
Bob retrieves the "StockControl" task details directly from the mcp server without manual input.

### Challenge 2: Architectural Integrity (BCE Pattern)

**Issue:** AI assistants often dump all logic into a single "God Class" or a messy Controller, ignoring project architectural standards.

**Solution:** Leverage the Quarkus Mode instructions to force a Boundary-Control-Entity separation.

**Prompt:**

> "Implement the next task from the backlog. Use the BCE pattern: create a Boundary for the REST API and a Control for the logic."

**Learning:**
By combining the **Backlog MCP** (the "What") with Bob's **Java Capabilities** (the "How"), Bob generates a structured directory tree separating the API, logic, and data layers.

### Challenge 3: Human in the Loop

**Issue:** Bob can mark tasks as done automatically when it finishes implementing them, but that skips a human review. The backlog may show work as complete before anyone has verified the code.

**Solution:** Keep the human in the loop: review the generated code first, then explicitly ask Bob to update the backlog only after you are satisfied.

**Prompt:**

> "I've reviewed the code. Mark the task as done in Backlog.md."

**Learning:**
The backlog stays an accurate record of *verified* work. You get closed-loop automation (Backlog → code → Backlog) with a quality gate: tasks move to "done" only after human sign-off.

---

## Final Outcome

**What was achieved:**

* **Zero Context Switching:** The developer stays in the terminal/Bob to check requirements.
* **Architectural Guardrails:** Code is generated following strict BCE patterns from the start.
* **Closed-Loop Automation:** Bob uses the MCP to mark the task as `[x] Done` in `Backlog.md` once finished.
* **Traceability:** The `Backlog.md` becomes a living document of the implementation journey.

