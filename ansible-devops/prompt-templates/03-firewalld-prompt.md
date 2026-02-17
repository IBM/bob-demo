# Configure Firewall

**Mode:** Ansible Developer

**Context:**

We want to protect all servers with a firewall so that only the httpd port is open.

**Prompt:**

```
Configure firewalld to open the httpd port and restart firewalld.
```

**Result:**

Bob added a new task to the `playbook.yaml` file that installs firewalld, configures it to accept traffic on the httpd port, and restarts firewalld.

**Follow-up Actions:**

- Explain the current Playbook

---
