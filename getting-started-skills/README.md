# Implementation Journey: [Getting Started with Skills]

This section introduces you to how to start writing Skills in Bob.

**Date added:** [04/13/2026]  
**Duration:** 10 min 
**Mode(s) Used:** *Advanced* mode

## Initial Goal

Create a Skill that returns a greeting to the code assistant.

---

## Step-by-Step Process

### Step 1: Open IBM Bob and create the Skills Layout

Create a `.bob` directory to configure Bob.

Then, inside the `.bob` directory, create the `skills` directory containing all the skills.

Finally, inside the `skills` directory, create a directory that will contain our Skill.
In this case, name the directory `greeting`, as this will be the same Skill **name**.

**Outcome:**

IBM Bob directory layout for creating Skills.

### Step 2: Create first Skill

Create a new file in the `greeting` directory named `SKILL.md` with the following content:

```md
----
name: greetings
description: Greetings skill invoked when the user wants to get a greetings from the code assistant
----

# Greetings

Use this Skill every time the user requests a greeting from the code assistant.

Always respond with: "Aloha from Skill"
```

Save the file and send the following prompt to Bob: "send me a greeting"

**Bob's response:** 

You'll see the message "Aloha from Skill" in the chat.


### Step 3: Runs a Command

At this point, we have a simple Skill definition that returns a message.
Let's compose the output of the message from the output of a command.
For this example, we'll use the `whoami` Linux command to get the current user and send a specific greeting.

Open `SKILL.md` file and replace the content with the following:

```md
----
name: greetings
description: Greetings skill invoked when the user wants to get a greetings from the code assistant
----

# Greetings

Use this Skill every time the user requests a greeting from the code assistant.

## Worflow

To generate a greeting follow the steps below:

1. Run the following command:

\```bash
whoami
\```

2. The assistant will respond with a greeting message in the following format:

"Aloha {user_name}!" where `{user_name}` is the output of the `whoami` command.
```

You can see that the Skill has an instruction to execute the `whoami` command.

Save the file and send the following prompt to Bob: "send me a greeting"

**Bob's response:** 

You'll see the message "Aloha <username>" in the chat.

### Step 4: Extracts the Script

In the previous example, the command is simple, but what happens if you need to execute multiple commands or implement more complex logic in a bash script?

Skills recommend putting these scripts not embedded into the Markdown file, but in its file and in the `scripts` directory.

So let's migrate the `whoami` command into a script.

Create a new directory in the `greeting` directory named `scripts`.
Then, create a new file `who.sh` inside the directory with the following content:

```bash
#!/bin/bash

whoami
```

Then change the `SKILL.md` file to invoke this script:

```md
----
name: greetings
description: Greetings skill invoked when the user wants to get a greetings from the code assistant
----

# Greetings

Use this Skill every time the user requests a greeting from the code assistant.

## Worflow

To generate a greeting follow the steps below:

1. Run the [script](./script/who.sh) command.

2. The assistant will respond with a greeting message in the following format:

"Aloha {user_name}!" where `{user_name}` is the output of the script command.
```

Notice that now in the first step, you are referring directly to the script. 

---

## Final Outcome

**What was achieved:**
- A basic knowledge to start developing Skills
- How to Use Skills in Bob
