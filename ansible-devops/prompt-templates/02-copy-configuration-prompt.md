# Copy httpd Configuration

**Mode:** Ansible Developer

**Context:**

We configure the httpd servers from all servers, copying the configuration template.

**Prompt:**

```
Add a task to copy the httpd.conf.j2 template to /etc/httpd/conf, and add the placeholders defined in the file to the vars section.
```

**Result:**

Bob added a new task in the `playbook.yaml` file, copied and materialized the template, added the variables, and finally registered a restart task for the httpd service to make the configuration change effective.

**Follow-up Actions:**

- Configure firewalld to accept traffic on the httpd port.

---
