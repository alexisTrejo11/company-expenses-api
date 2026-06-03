---
layers:
  - name: "Presentation (clients)"
    description: "Web or mobile clients for employees, managers, finance, and admins."
    color: "#6366F1"
    expanded: true
    components:
      - "Employee expense submission UI"
      - "Manager approval queue"
      - "Admin settings / dashboard"
    responsibilities:
      - "Store JWT after login"
      - "Render ResponseWrapper success and error states"
    technologies:
      - "HTTPS"
      - "REST JSON"
      - "OpenAPI-generated client (optional)"

  - name: "API & security"
    description: "Spring MVC controllers, validation, JWT resource server, and global rate limiting."
    color: "#10B981"
    expanded: true
    components:
      - "AuthController — registration & login"
      - "EmployeeExpenseController / ExpenseAttachmentController"
      - "ExpenseController — manager workflow"
      - "ReimbursementController"
      - "NotificationController"
      - "AdminController"
      - "UserController"
      - "SecurityConfig + JWTService"
      - "RateLimitInterceptor"
    responsibilities:
      - "HTTP mapping and DTO validation"
      - "Role-based authorization per path prefix"
      - "Consistent ResponseWrapper envelopes"
    technologies:
      - "Spring Web MVC"
      - "Spring Security OAuth2 Resource Server"
      - "Bucket4j"
      - "springdoc OpenAPI"

  - name: "Application services"
    description: "Business logic, notifications, email, and file uploads."
    color: "#F59E0B"
    expanded: true
    components:
      - "ExpenseService"
      - "AuthService / UserService"
      - "ReimbursementService"
      - "NotificationService"
      - "AdminService"
      - "AttachmentService"
      - "EmailService"
      - "FileHandler"
    responsibilities:
      - "Expense lifecycle and validation"
      - "Trigger notifications on state changes"
      - "Map entities to DTOs (MapStruct)"
    technologies:
      - "Spring @Service"
      - "MapStruct"
      - "Thymeleaf (email templates)"

  - name: "Persistence"
    description: "JPA entities, repositories, and Flyway-managed PostgreSQL schema."
    color: "#EF4444"
    expanded: true
    components:
      - "User, Expense, ExpenseAttachment"
      - "Reimbursement, Notification"
      - "AdminSettings"
      - "ExpenseRepository and related repos"
    responsibilities:
      - "ACID transactions for financial data"
      - "Soft delete on expenses"
      - "Schema migrations at startup"
    technologies:
      - "Spring Data JPA"
      - "Hibernate"
      - "Flyway"
      - "PostgreSQL"

designPatterns:
  - title: "Layered architecture"
    emoji: "🏗️"
    description: "Controllers delegate to services; services use repositories and mappers—classic Spring layering."
    category: "Structural"
    badge: "Core"
  - title: "DTO + Mapper"
    emoji: "🔄"
    description: "MapStruct mappers translate entities to API DTOs, keeping persistence models out of HTTP contracts."
    category: "Structural"
    badge: "MapStruct"
  - title: "Result object"
    emoji: "✅"
    description: "Result<T> wraps success/failure for auth validation without exceptions for expected failures."
    category: "Behavioral"
    badge: "shared/Result"
  - title: "Response wrapper"
    emoji: "📦"
    description: "ResponseWrapper factory methods standardize success, created, notFound, and badRequest payloads."
    category: "Structural"
    badge: "API"
  - title: "Repository"
    emoji: "🗄️"
    description: "Spring Data JPA repositories encapsulate queries and pagination."
    category: "Data"
    badge: "JPA"
  - title: "Interceptor — rate limit"
    emoji: "🚦"
    description: "HandlerInterceptor consumes Bucket4j tokens before controller execution."
    category: "Behavioral"
    badge: "Bucket4j"

scalabilityStrategies:
  - title: "Stateless API instances"
    description: "JWT-backed auth allows horizontal scaling of Spring Boot containers behind a load balancer."
  - title: "External PostgreSQL"
    description: "Database lives outside the app container in production compose—scale app tier independently."
  - title: "Paginated list endpoints"
    description: "Manager and employee list endpoints use Spring Data Pageable to limit payload size."
  - title: "File storage externalization (future)"
    description: "Move FILE_UPLOAD_DIR to S3-compatible object storage for multi-instance deploys."

securityStrategies:
  - title: "JWT resource server"
    description: "Bearer tokens validated per request; roles claim drives hasRole checks."
  - title: "Path-based authorization"
    description: "Separate prefixes for admin, manager, employee, and reimbursement routes."
  - title: "Password hashing"
    description: "PasswordHandler for credential storage (see UserService / auth flow)."
  - title: "Global rate limiting"
    description: "Mitigates brute-force and abuse via Bucket4j 429 responses."
  - title: "CSRF disabled"
    description: "Appropriate for pure JSON API clients; ensure HTTPS-only in production."

cacheStrategies:
  - name: "Caffeine (Spring Cache)"
    description: "spring-boot-starter-cache with Caffeine on classpath—ready for @Cacheable on hot reads."
    ttl: "Per-cache config (placeholder)"
    coverage: "Not widely applied in controllers yet"
  - name: "No Redis in current deploy"
    description: "Unlike Django template docs, this project does not bundle Redis; rate limits are in-process Bucket4j."
    ttl: "N/A"
    coverage: "Single-instance effective only"

architectureFeatures:
  - title: "Unified API envelope"
    emoji: "📦"
    description: "ResponseWrapper provides consistent JSON for clients and tests."
  - title: "OpenAPI-first exploration"
    emoji: "📖"
    description: "springdoc + @Operation document endpoints at /swagger-ui.html."
  - title: "Flyway versioned schema"
    emoji: "🗃️"
    description: "SQL migrations in src/main/resources/db/migration."
  - title: "Role-separated API surface"
    emoji: "🧩"
    description: "Distinct controller packages/paths per actor: employee, manager, admin, finance."

architectureDiagram:
  legendItems:
    - type: "client"
      label: "Client"
      color: "#6366F1"
      icon: "monitor"
    - type: "gateway"
      label: "Edge (optional)"
      color: "#10B981"
      icon: "shield"
    - type: "service"
      label: "API service"
      color: "#F59E0B"
      icon: "server"
    - type: "database"
      label: "Database"
      color: "#EF4444"
      icon: "database"
    - type: "queue"
      label: "Files / mail"
      color: "#8B5CF6"
      icon: "layers"

  nodes:
    - id: "clients"
      label: "Web / mobile clients"
      type: "client"
      x: 80
      y: 120
      connections: ["api"]
      status: "healthy"
      traffic: 100
    - id: "api"
      label: "Spring Boot API :8080"
      type: "service"
      x: 320
      y: 120
      connections: ["postgres", "files", "smtp"]
      status: "healthy"
      traffic: 90
    - id: "postgres"
      label: "PostgreSQL"
      type: "database"
      x: 560
      y: 80
      connections: []
      status: "healthy"
      traffic: 70
    - id: "files"
      label: "FILE_UPLOAD_DIR"
      type: "queue"
      x: 560
      y: 180
      connections: []
      status: "healthy"
      traffic: 25
    - id: "smtp"
      label: "SMTP server"
      type: "monitoring"
      x: 320
      y: 240
      connections: []
      status: "healthy"
      traffic: 15
    - id: "swagger"
      label: "Swagger UI"
      type: "client"
      x: 80
      y: 240
      connections: ["api"]
      status: "healthy"
      traffic: 10

  connections:
    - id: "c1"
      from: "clients"
      to: "api"
      label: "HTTPS + JWT"
      protocol: "REST"
      isActive: true
    - id: "c2"
      from: "api"
      to: "postgres"
      label: "JDBC"
      protocol: "PostgreSQL"
      isActive: true
    - id: "c3"
      from: "api"
      to: "files"
      label: "Upload receipts"
      protocol: "Local FS"
      isActive: true
    - id: "c4"
      from: "api"
      to: "smtp"
      label: "Email"
      protocol: "SMTP"
      isActive: true
    - id: "c5"
      from: "swagger"
      to: "api"
      label: "Docs"
      protocol: "HTTP"
      isActive: true

dataFlow:
  requestFlow:
    - number: 1
      title: "Client request"
      description: "Client calls REST endpoint with JSON body and optional Bearer JWT."
      icon: "send"
    - number: 2
      title: "Rate limit & security"
      description: "RateLimitInterceptor consumes token; SecurityFilterChain validates JWT and roles."
      icon: "shield"
    - number: 3
      title: "Controller → service"
      description: "Controller validates DTOs, delegates to @Service (e.g. ExpenseService.createExpense)."
      icon: "cog"
    - number: 4
      title: "Persistence"
      description: "JPA repositories write/read PostgreSQL; Flyway ensures schema version."
      icon: "database"
    - number: 5
      title: "Response"
      description: "ResponseWrapper JSON returned; notifications/email may fire asynchronously in-thread."
      icon: "reply"

  eventFlow:
    - number: 1
      title: "Expense state change"
      description: "Submit, approve, or reject updates expense row and may create notification row."
      icon: "zap"
    - number: 2
      title: "NotificationService"
      description: "builds in-app notification for affected user."
      icon: "bell"
    - number: 3
      title: "Email (optional)"
      description: "EmailService can send templated mail when SMTP configured."
      icon: "mail"
    - number: 4
      title: "Reimbursement"
      description: "Finance creates reimbursement record linked to expense."
      icon: "wallet"

techDecisions:
  - title: "Spring Boot monolith"
    problem: "Small team needs one deployable unit with clear modules, not microservice overhead."
    solution: "Single Gradle project with controller/service/repository packages."
    alternatives:
      - "Microservices per domain"
      - "Serverless functions"
    outcome: "Faster iteration; Docker one-jar deploy."
    icon: "layers"
  - title: "PostgreSQL + Flyway"
    problem: "Relational expense data with FK integrity and audit fields."
    solution: "PostgreSQL with versioned SQL migrations instead of ddl-auto in prod."
    alternatives:
      - "H2 only (tests)"
      - "MongoDB documents"
    outcome: "Predictable schema; demo seed in V2 migration."
    icon: "database"
  - title: "JWT (OAuth2 resource server)"
    problem: "SPA/mobile clients need stateless auth across instances."
    solution: "Encode roles in JWT; validate with JwtDecoder per request."
    alternatives:
      - "Session cookies"
      - "API keys only"
    outcome: "Works with horizontal scaling; no refresh token flow yet."
    icon: "key"
  - title: "In-process rate limiting"
    problem: "Basic abuse protection without Redis infrastructure."
    solution: "Bucket4j bucket in RateLimitInterceptor for all paths."
    alternatives:
      - "Redis-backed limits"
      - "API gateway throttling"
    outcome: "Simple for single instance; per-instance counters when scaled out."
    icon: "gauge"
---

> **Context:** Corporate expense reimbursement—not clinical/MindCare domain. Diagram nodes reflect actual Spring Boot + Postgres + local file uploads.

> **Deploy alignment:** Matches `docker/docker-compose.local.yml` and `docker-compose.prod.yml` in this repository.

> **Technical debt:** No API gateway in repo (optional Nginx/ALB in production); FINANCE vs FINANCIAL role mismatch; approve endpoint path typo; consider Spring Boot Actuator for health probes.

> **Useful diagram to add:** Sequence chart from expense submit → manager approve → reimbursement → notification.
