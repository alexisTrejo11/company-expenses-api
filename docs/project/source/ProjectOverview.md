---
problemStatement:
  problemTitle: "Manual, opaque expense reimbursement workflows"
  problemDescription: "Companies often track employee expenses in spreadsheets or email threads. Managers lack a single queue for pending approvals, finance cannot tie reimbursements to approved claims, and employees have no audit trail for receipts or rejection reasons."
  problemList:
    - "Expense status scattered across email and spreadsheets"
    - "No role-separated API for employees vs managers vs finance vs admins"
    - "Receipt attachments stored ad hoc without linkage to expense records"
    - "Managers need paginated filters by status and monthly summaries"
    - "No standardized JSON envelope for frontend clients"

solution:
  solutionTitle: "A Spring Boot API for the full expense lifecycle"
  solutionList:
    - title: "JWT auth with role-based routes"
      description: "Register employees and managers publicly; admins register other admins. OAuth2 resource server validates Bearer tokens with roles claim mapped to Spring authorities."
    - title: "Employee expense submission"
      description: "Authenticated employees create PENDING expenses, upload multipart attachments, and list their own claims via JWT subject email."
    - title: "Manager approval workflow"
      description: "Managers and admins approve or reject expenses, soft-delete records, filter by status with sorting, and fetch date-range summaries."
    - title: "Finance reimbursements"
      description: "MANAGER or FINANCE roles create reimbursements linked to expenses; notifications fire on state changes."
    - title: "Admin settings & dashboard"
      description: "Admin-only dashboard stats and mutable company settings stored in the database."
    - title: "Containerized local and cloud deploy"
      description: "Multi-stage Docker image, local compose with PostgreSQL 15, production compose with external DB host from `.env`."

keyMetrics:
  metricsTitle: "Codebase snapshot"
  metricsList:
    - "8 REST controllers under `/v1/api/*`"
    - "~75 main Java source files + 11 test classes"
    - "2 Flyway SQL migrations (schema + demo seed data)"
    - "API served on port 8080; Swagger UI at `/swagger-ui.html`"
    - "Global Bucket4j interceptor on `/**` (20 token bucket, 10 refill/min)"

links:
  github: "https://github.com/alexisTrejo11/company-expenses-api"
  demo: "http://localhost:8080/swagger-ui.html"
  documentation: "http://localhost:8080/api-docs"
  dockerHub: null

mediaGallery:
  title: "Company Expenses API — product views"
  description: "Placeholder assets for portfolio UI. Replace with Swagger screenshots, admin dashboard mockups, or sequence diagrams from your frontend."
  items:
    - type: "image"
      url: "https://placehold.co/1200x630/1e3a5f/ffffff?text=Company+Expenses+API"
      thumbnail: "https://placehold.co/400x210/1e3a5f/ffffff?text=Expenses+API"
      title: "API cover"
      description: "Spring Boot expense tracking backend"
      alt: "Company Expenses API branding placeholder"
      category: "screenshot"
    - type: "image"
      url: "https://placehold.co/1200x800/2563eb/ffffff?text=Swagger+UI"
      thumbnail: "https://placehold.co/400x267/2563eb/ffffff?text=OpenAPI"
      title: "OpenAPI documentation"
      description: "springdoc Swagger UI for interactive endpoint exploration"
      alt: "Swagger UI placeholder"
      category: "demo"

mediaItems:
  - type: "image"
    url: "https://placehold.co/800x500/059669/ffffff?text=Docker+Local"
    thumbnail: "https://placehold.co/320x200/059669/ffffff?text=Docker"
    title: "Docker local stack"
    description: "App + PostgreSQL via docker/docker-compose.local.yml"
    alt: "Docker compose placeholder"
    category: "architecture"
  - type: "image"
    url: "https://placehold.co/800x500/7c3aed/ffffff?text=Expense+Flow"
    thumbnail: "https://placehold.co/320x200/7c3aed/ffffff?text=Flow"
    title: "Expense approval flow"
    description: "PENDING → APPROVED/REJECTED → REIMBURSED lifecycle"
    alt: "Workflow diagram placeholder"
    category: "diagram"

metrics:
  - label: "Spring Boot"
    value: "3.4.1"
    description: "Parent BOM in build.gradle"
  - label: "Java toolchain"
    value: "17"
    description: "Gradle java.toolchain"
  - label: "API prefix"
    value: "/v1/api"
    description: "All business controllers"
  - label: "JWT TTL default"
    value: "24h"
    description: "jwt.expiration.ms default 86400000 in application.properties"
---

> **Highlight:** Consistent `ResponseWrapper` JSON (`success`, `data`, `message`, `code`, `time_stamp`) across most endpoints simplifies client error handling.

> **Gaps / tech debt to document for reviewers:**
> 1. **No dedicated health actuator** — use Swagger or a future `/actuator/health` for probes.
> 2. **Role mismatch:** `SecurityConfig` references `FINANCIAL` but `Role` enum defines `FINANCE` — reimbursement routes may not authorize finance users until aligned.
> 3. **Path inconsistencies:** `AuthController` uses `v1/api/auth` without leading slash; `ExpenseController` approve mapping is `PUT .../expenses{expenseId}/approve` (missing `/` before `{expenseId}`).
> 4. **Manager `by-user` endpoint** declares `@PathVariable String email` on path `by-user/{userId}` — path variable name mismatch.
> 5. **PostgreSQL driver** — confirm runtime JDBC dependency if removing `spring-ai-postgresml` starter.
> 6. **Caffeine cache** is on classpath but domain caching strategy is not fully documented in code—placeholder for future read-heavy endpoints.

> **Useful additions:** Postman collection export from OpenAPI, ER diagram from Flyway V1, and frontend repo link when available.
