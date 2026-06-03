# Project Features

## JWT authentication & role-based access

OAuth2 resource server with JWT decoder; roles claim mapped to Spring authorities (ROLE_EMPLOYEE, ROLE_MANAGER, ROLE_ADMIN, ROLE_FINANCE). Auth endpoints issue tokens on register/login.

| Property | Value |
| --- | --- |
| ID | jwt-auth-rbac |
| Category | authentication |
| Status | stable |
| Icon | shield-lock |

### Highlights

- Public /v1/api/auth/login and register-employee/manager
- Admin-only register-admin with @PreAuthorize
- Stateless session; CSRF disabled for API usage

### Tech stack

- Spring Security 6
- nimbus-jose-jwt
- JWTService

### Metrics

| Label | Value | Trend |
| --- | --- | --- |
| Token TTL default | 24h | stable |

### Code snippet

_config/security/SecurityConfig.java_

```java
.requestMatchers("/v1/api/auth/**", "/swagger-ui/**", "/api-docs/**").permitAll()
.requestMatchers("/v1/api/admin/**").hasRole("ADMIN")
.requestMatchers("/v1/api/manager/**").hasAnyRole("MANAGER", "ADMIN")
.requestMatchers("/v1/api/reimbursements/**").hasAnyRole("MANAGER", "FINANCIAL")
```

## Expense submission & approval

Employees create PENDING expenses; managers approve, reject with reason, or soft-delete; status transitions trigger notifications.

| Property | Value |
| --- | --- |
| ID | expense-workflow |
| Category | api |
| Status | stable |
| Icon | receipt |

### Highlights

- Categories: TRAVEL, FOOD, OFFICE_SUPPLIES, SOFTWARE_LICENSES, ENTERTAINMENT
- Statuses: PENDING, APPROVED, REJECTED, REIMBURSED, MISCELLANEOUS
- Paginated manager views by status and user

### Tech stack

- ExpenseService
- ExpenseController / EmployeeExpenseController
- Flyway schema

### Metrics

| Label | Value | Trend |
| --- | --- | --- |
| Default page size | 10 | stable |

## Receipt attachments

Multipart uploads stored via FileHandler to configurable FILE_UPLOAD_DIR; metadata in expense_attachments table.

| Property | Value |
| --- | --- |
| ID | attachments |
| Category | integration |
| Status | stable |
| Icon | paperclip |

### Highlights

- POST /v1/api/employees/expenses/{id}/attachments
- commons-io for file handling
- Cascade delete on expense_attachments FK

### Tech stack

- FileHandler
- AttachmentService
- commons-io

## Reimbursement processing

Finance/manager roles record reimbursements against approved expenses with processed_by audit.

| Property | Value |
| --- | --- |
| ID | reimbursements |
| Category | api |
| Status | beta |
| Icon | wallet |

### Highlights

- POST /v1/api/reimbursements
- Links expense_id and processed_by user
- Notification on create

### Tech stack

- ReimbursementService
- ReimbursementRepository

### Metrics

| Label | Value | Trend |
| --- | --- | --- |
| Authorized roles | MANAGER + FINANCIAL* | stable |

## In-app notifications & email

Notification entities for user events; EmailService + Thymeleaf templates for outbound mail when configured.

| Property | Value |
| --- | --- |
| ID | notifications-email |
| Category | messaging |
| Status | beta |
| Icon | bell |

### Highlights

- CRUD-style notification HTTP API
- sendNotificationFromExpense on approve/reject/submit
- SMTP settings from .env

### Tech stack

- NotificationService
- spring-boot-starter-mail
- Thymeleaf

## Global API rate limiting

Bucket4j classic bucket applied to all routes through WebMvc interceptor; returns 429 with Retry-After style header.

| Property | Value |
| --- | --- |
| ID | rate-limiting |
| Category | performance |
| Status | stable |
| Icon | speedometer |

### Highlights

- 20 token capacity, 10 refill per minute
- X-Rate-Limit-Remaining response header
- JSON error body on quota exhaustion

### Tech stack

- bucket4j-core
- RateLimitInterceptor

### Code snippet

_config/rateLimiter/RateLimiterConfig.java_

```java
Refill refill = Refill.intervally(10, Duration.ofMinutes(1));
Bandwidth limit = Bandwidth.classic(20, refill);
return Bucket.builder().addLimit(limit).build();
```

## Database migrations

Flyway manages PostgreSQL schema; V1 creates users, expenses, attachments, reimbursements, notifications; V2 seeds demo data.

| Property | Value |
| --- | --- |
| ID | flyway-migrations |
| Category | database |
| Status | stable |
| Icon | database |

### Highlights

- spring.flyway.enabled=true
- CHECK constraints on category and status
- Soft delete via expenses.deleted_at

### Tech stack

- Flyway
- PostgreSQL 15

## OpenAPI & Swagger UI

springdoc-openapi exposes /api-docs and /swagger-ui.html for interactive testing.

| Property | Value |
| --- | --- |
| ID | openapi-docs |
| Category | api |
| Status | stable |
| Icon | book |

### Highlights

- @Operation annotations on controllers
- OpenApiConfig metadata (title Expense Tracking API v1.0)
- Public access in SecurityConfig

### Tech stack

- springdoc-openapi-starter-webmvc-ui

### Metrics

| Label | Value | Trend |
| --- | --- | --- |
| Documented version | 1.0 | stable |

## Docker & Compose profiles

Gradle multi-stage build producing boot JAR; local compose runs app + Postgres; prod compose runs app only against external DB.

| Property | Value |
| --- | --- |
| ID | docker-deploy |
| Category | integration |
| Status | stable |
| Icon | docker |

### Highlights

- docker/Dockerfile — JDK 17 build, Temurin 17 JRE runtime
- docker-compose.local.yml — Postgres healthcheck on 5431
- docker-compose.prod.yml — external DB_HOST

### Tech stack

- Docker
- docker/README.md

## Additional notes

> **Stable:** Auth, expense CRUD workflow, attachments, Flyway, Swagger, Docker local stack.

> **Beta:** Reimbursements role naming (`FINANCE` vs `FINANCIAL`), email delivery depends on valid SMTP in `.env`, notification templates coverage incomplete in repo.

> **Experimental / placeholder:** Spring AI postgresml starter on classpath—purpose for expense domain unclear; consider removing if unused.

> **Before production:** Fix approve URL mapping slash, align finance role name, add actuator health, explicit PostgreSQL driver, and secure register-manager endpoint (currently public).

