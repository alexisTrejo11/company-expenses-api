# Project Overview

## Manual, opaque expense reimbursement workflows

Companies often track employee expenses in spreadsheets or email threads. Managers lack a single queue for pending approvals, finance cannot tie reimbursements to approved claims, and employees have no audit trail for receipts or rejection reasons.

### Pain points

- Expense status scattered across email and spreadsheets
- No role-separated API for employees vs managers vs finance vs admins
- Receipt attachments stored ad hoc without linkage to expense records
- Managers need paginated filters by status and monthly summaries
- No standardized JSON envelope for frontend clients

## A Spring Boot API for the full expense lifecycle

- **JWT auth with role-based routes** — Register employees and managers publicly; admins register other admins. OAuth2 resource server validates Bearer tokens with roles claim mapped to Spring authorities.
- **Employee expense submission** — Authenticated employees create PENDING expenses, upload multipart attachments, and list their own claims via JWT subject email.
- **Manager approval workflow** — Managers and admins approve or reject expenses, soft-delete records, filter by status with sorting, and fetch date-range summaries.
- **Finance reimbursements** — MANAGER or FINANCE roles create reimbursements linked to expenses; notifications fire on state changes.
- **Admin settings & dashboard** — Admin-only dashboard stats and mutable company settings stored in the database.
- **Containerized local and cloud deploy** — Multi-stage Docker image, local compose with PostgreSQL 15, production compose with external DB host from `.env`.

## Codebase snapshot

- 8 REST controllers under `/v1/api/*`
- ~75 main Java source files + 11 test classes
- 2 Flyway SQL migrations (schema + demo seed data)
- API served on port 8080; Swagger UI at `/swagger-ui.html`
- Global Bucket4j interceptor on `/**` (20 token bucket, 10 refill/min)

## Links

| Resource | URL |
| --- | --- |
| Github | https://github.com/alexisTrejo11/company-expenses-api |
| Demo | http://localhost:8080/swagger-ui.html |
| Documentation | http://localhost:8080/api-docs |
| Dockerhub | None |

## Company Expenses API — product views

Placeholder assets for portfolio UI. Replace with Swagger screenshots, admin dashboard mockups, or sequence diagrams from your frontend.

### API cover

Spring Boot expense tracking backend

- **Type:** image | **Category:** screenshot
- ![Company Expenses API branding placeholder](https://placehold.co/1200x630/1e3a5f/ffffff?text=Company+Expenses+API)

### OpenAPI documentation

springdoc Swagger UI for interactive endpoint exploration

- **Type:** image | **Category:** demo
- ![Swagger UI placeholder](https://placehold.co/1200x800/2563eb/ffffff?text=Swagger+UI)

## Additional media

### Docker local stack

App + PostgreSQL via docker/docker-compose.local.yml

### Expense approval flow

PENDING → APPROVED/REJECTED → REIMBURSED lifecycle

## Metrics

| Label | Value | Description |
| --- | --- | --- |
| Spring Boot | 3.4.1 | Parent BOM in build.gradle |
| Java toolchain | 17 | Gradle java.toolchain |
| API prefix | /v1/api | All business controllers |
| JWT TTL default | 24h | jwt.expiration.ms default 86400000 in application.properties |

## Additional notes

> **Highlight:** Consistent `ResponseWrapper` JSON (`success`, `data`, `message`, `code`, `time_stamp`) across most endpoints simplifies client error handling.

> **Gaps / tech debt to document for reviewers:**
> 1. **No dedicated health actuator** — use Swagger or a future `/actuator/health` for probes.
> 2. **Role mismatch:** `SecurityConfig` references `FINANCIAL` but `Role` enum defines `FINANCE` — reimbursement routes may not authorize finance users until aligned.
> 3. **Path inconsistencies:** `AuthController` uses `v1/api/auth` without leading slash; `ExpenseController` approve mapping is `PUT .../expenses{expenseId}/approve` (missing `/` before `{expenseId}`).
> 4. **Manager `by-user` endpoint** declares `@PathVariable String email` on path `by-user/{userId}` — path variable name mismatch.
> 5. **PostgreSQL driver** — confirm runtime JDBC dependency if removing `spring-ai-postgresml` starter.
> 6. **Caffeine cache** is on classpath but domain caching strategy is not fully documented in code—placeholder for future read-heavy endpoints.

> **Useful additions:** Postman collection export from OpenAPI, ER diagram from Flyway V1, and frontend repo link when available.

