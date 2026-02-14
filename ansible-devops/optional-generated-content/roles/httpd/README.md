# Ansible Role: httpd

This role installs and configures Apache HTTP Server (httpd) on RHEL/CentOS/Fedora systems with proper firewall configuration.

## Requirements

- Ansible 2.9 or higher
- Target systems: RHEL/CentOS 7/8/9 or Fedora
- The `ansible.posix` collection must be installed for firewalld management

## Role Variables

All variables are defined in [`defaults/main.yml`](defaults/main.yml) and can be overridden:

### Apache Configuration Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `httpd_server_root` | `/etc/httpd` | Apache's base directory |
| `httpd_listen` | `80` | Port for HTTP traffic |
| `httpd_server_admin` | `webmaster@localhost` | Administrator email address |
| `httpd_server_name` | `{{ ansible_fqdn }}` | Server's fully qualified domain name |
| `httpd_server_tokens` | `Prod` | Server signature in HTTP headers (Prod = minimal disclosure) |
| `httpd_document_root` | `/var/www/html` | Directory where web files are served from |
| `httpd_error_log` | `logs/error_log` | Error log path (relative to ServerRoot) |
| `httpd_log_level` | `warn` | Logging verbosity level |
| `httpd_access_log` | `logs/access_log` | Access log path (relative to ServerRoot) |

## Dependencies

None.

## Example Playbook

### Basic Usage

```yaml
---
- name: Configure Webservers
  hosts: webservers
  become: true
  roles:
    - httpd
```

### With Custom Variables

```yaml
---
- name: Configure Webservers with custom settings
  hosts: webservers
  become: true
  roles:
    - role: httpd
      vars:
        httpd_listen: "8080"
        httpd_server_admin: "admin@example.com"
        httpd_server_name: "www.example.com"
        httpd_log_level: "info"
```

### Using vars in Playbook

```yaml
---
- name: Configure Webservers
  hosts: webservers
  become: true
  vars:
    httpd_server_admin: "sysadmin@company.com"
    httpd_document_root: "/var/www/myapp"
  roles:
    - httpd
```

## What This Role Does

1. **Installs httpd package** - Uses distribution-agnostic package module
2. **Deploys configuration** - Applies Jinja2 template with your variables
3. **Configures firewall** - Installs and configures firewalld to allow HTTP traffic
4. **Manages services** - Ensures httpd and firewalld are running and enabled

## Handlers

The role includes two handlers that are triggered only when changes occur:

- **Restart httpd** - Restarts Apache when configuration changes
- **Restart firewalld** - Restarts firewall when rules are modified

## Role Structure

```
roles/httpd/
├── defaults/
│   └── main.yml          # Default variables
├── handlers/
│   └── main.yml          # Service restart handlers
├── meta/
│   └── main.yml          # Role metadata
├── tasks/
│   └── main.yml          # Main task list
├── templates/
│   └── httpd.conf.j2     # Apache configuration template
└── README.md             # This file
```

## Security Considerations

- **ServerTokens** set to `Prod` minimizes information disclosure
- **File permissions** on httpd.conf set to 0644 (read-only for non-root)
- **Firewall** configured to only allow HTTP traffic
- **Log level** set to `warn` by default to balance security and debugging

## Customization

To customize the Apache configuration beyond the provided variables, you can:

1. Override variables in your playbook or inventory
2. Modify the template file [`templates/httpd.conf.j2`](templates/httpd.conf.j2)
3. Add additional tasks in your playbook after including this role

## Testing

To test this role:

```bash
# Check syntax
ansible-playbook playbook.yaml --syntax-check

# Run in check mode (dry-run)
ansible-playbook playbook.yaml --check

# Run the playbook
ansible-playbook playbook.yaml
```

## License

MIT

## Author Information

Created by Ansible Developer for infrastructure automation.