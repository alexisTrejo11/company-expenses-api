---
features:
  - id: "jwt-auth-rbac"
    title: "JWT authentication & role-based access"
    description: "OAuth2 resource server with JWT decoder; roles claim mapped to Spring authorities (ROLE_EMPLOYEE, ROLE_MANAGER, ROLE_ADMIN, ROLE_FINANCE). Auth endpoints issue tokens on register/login."
    icon: "shield-lock"
    category: "authentication"
    status: "stable"
    highlights:
      - "Public /v1/api/auth/login and register-employee/manager"
      - "Admin-only register-admin with @PreAuthorize"
      - "Stateless session; CSRF disabled for API usage"
    techStack:
      - "Spring Security 6"
      - "nimbus-jose-jwt"
      - "JWTService"
    metrics:
      - label: "Token TTL default"
        value: "24h"
        trend: "stable"
        icon: "clock"
    codeSnippet:
      language: "java"
      filename: "config/security/SecurityConfig.java"
      code: |
        .requestMatchers("/v1/api/auth/**", "/swagger-ui/**", "/api-docs/**").permitAll()
        .requestMatchers("/v1/api/admin/**").hasRole("ADMIN")
        .requestMatchers("/v1/api/manager/**").hasAnyRole("MANAGER", "ADMIN")
        .requestMatchers("/v1/api/reimbursements/**").hasAnyRole("MANAGER", "FINANCIAL")

  - id: "expense-workflow"
    title: "Expense submission & approval"
    description: "Employees create PENDING expenses; managers approve, reject with reason, or soft-delete; status transitions trigger notifications."
    icon: "receipt"
    category: "api"
    status: "stable"
    highlights:
      - "Categories: TRAVEL, FOOD, OFFICE_SUPPLIES, SOFTWARE_LICENSES, ENTERTAINMENT"
      - "Statuses: PENDING, APPROVED, REJECTED, REIMBURSED, MISCELLANEOUS"
      - "Paginated manager views by status and user"
    techStack:
      - "ExpenseService"
      - "ExpenseController / EmployeeExpenseController"
      - "Flyway schema"
    metrics:
      - label: "Default page size"
        value: "10"
        trend: "stable"
        icon: "list"

  - id: "attachments"
    title: "Receipt attachments"
    description: "Multipart uploads stored via FileHandler to configurable FILE_UPLOAD_DIR; metadata in expense_attachments table."
    icon: "paperclip"
    category: "integration"
    status: "stable"
    highlights:
      - "POST /v1/api/employees/expenses/{id}/attachments"
      - "commons-io for file handling"
      - "Cascade delete on expense_attachments FK"
    techStack:
      - "FileHandler"
      - "AttachmentService"
      - "commons-io"

  - id: "reimbursements"
    title: "Reimbursement processing"
    description: "Finance/manager roles record reimbursements against approved expenses with processed_by audit."
    icon: "wallet"
    category: "api"
    status: "beta"
    highlights:
      - "POST /v1/api/reimbursements"
      - "Links expense_id and processed_by user"
      - "Notification on create"
    techStack:
      - "ReimbursementService"
      - "ReimbursementRepository"
    metrics:
      - label: "Authorized roles"
        value: "MANAGER + FINANCIAL*"
        trend: "stable"
        icon: "users"

  - id: "notifications-email"
    title: "In-app notifications & email"
    description: "Notification entities for user events; EmailService + Thymeleaf templates for outbound mail when configured."
    icon: "bell"
    category: "messaging"
    status: "beta"
    highlights:
      - "CRUD-style notification HTTP API"
      - "sendNotificationFromExpense on approve/reject/submit"
      - "SMTP settings from .env"
    techStack:
      - "NotificationService"
      - "spring-boot-starter-mail"
      - "Thymeleaf"

  - id: "rate-limiting"
    title: "Global API rate limiting"
    description: "Bucket4j classic bucket applied to all routes through WebMvc interceptor; returns 429 with Retry-After style header."
    icon: "speedometer"
    category: "performance"
    status: "stable"
    highlights:
      - "20 token capacity, 10 refill per minute"
      - "X-Rate-Limit-Remaining response header"
      - "JSON error body on quota exhaustion"
    techStack:
      - "bucket4j-core"
      - "RateLimitInterceptor"
    codeSnippet:
      language: "java"
      filename: "config/rateLimiter/RateLimiterConfig.java"
      code: |
        Refill refill = Refill.intervally(10, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(20, refill);
        return Bucket.builder().addLimit(limit).build();

  - id: "flyway-migrations"
    title: "Database migrations"
    description: "Flyway manages PostgreSQL schema; V1 creates users, expenses, attachments, reimbursements, notifications; V2 seeds demo data."
    icon: "database"
    category: "database"
    status: "stable"
    highlights:
      - "spring.flyway.enabled=true"
      - "CHECK constraints on category and status"
      - "Soft delete via expenses.deleted_at"
    techStack:
      - "Flyway"
      - "PostgreSQL 15"

  - id: "openapi-docs"
    title: "OpenAPI & Swagger UI"
    description: "springdoc-openapi exposes /api-docs and /swagger-ui.html for interactive testing."
    icon: "book"
    category: "api"
    status: "stable"
    highlights:
      - "@Operation annotations on controllers"
      - "OpenApiConfig metadata (title Expense Tracking API v1.0)"
      - "Public access in SecurityConfig"
    techStack:
      - "springdoc-openapi-starter-webmvc-ui"
    metrics:
      - label: "Documented version"
        value: "1.0"
        trend: "stable"
        icon: "tag"

  - id: "docker-deploy"
    title: "Docker & Compose profiles"
    description: "Gradle multi-stage build producing boot JAR; local compose runs app + Postgres; prod compose runs app only against external DB."
    icon: "docker"
    category: "integration"
    status: "stable"
    highlights:
      - "docker/Dockerfile — JDK 17 build, Temurin 17 JRE runtime"
      - "docker-compose.local.yml — Postgres healthcheck on 5431"
      - "docker-compose.prod.yml — external DB_HOST"
    techStack:
      - "Docker"
      - "docker/README.md"
---

> **Stable:** Auth, expense CRUD workflow, attachments, Flyway, Swagger, Docker local stack.

> **Beta:** Reimbursements role naming (`FINANCE` vs `FINANCIAL`), email delivery depends on valid SMTP in `.env`, notification templates coverage incomplete in repo.

> **Experimental / placeholder:** Spring AI postgresml starter on classpath—purpose for expense domain unclear; consider removing if unused.

> **Before production:** Fix approve URL mapping slash, align finance role name, add actuator health, explicit PostgreSQL driver, and secure register-manager endpoint (currently public).
