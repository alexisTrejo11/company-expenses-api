---
metrics:
  - label: "API port"
    value: "8080"
    icon: "server"
    description: "server.port in application.properties; exposed in Docker compose"
  - label: "Local Postgres host port"
    value: "5431"
    icon: "database"
    description: "Maps container 5432 for host access during Gradle bootRun"
  - label: "Java runtime"
    value: "17"
    icon: "cpu"
    description: "Eclipse Temurin JRE in production image"
  - label: "Flyway migrations"
    value: "2"
    icon: "migrate"
    description: "V1 schema + V2 demo seed"

cloudServices:
  - name: "PostgreSQL (managed or self-hosted)"
    purpose: "Primary datastore for users, expenses, reimbursements, notifications, admin settings"
    icon: "database"
    cost: "~$0 local Docker — ~$15–50/mo managed (placeholder)"
  - name: "SMTP provider"
    purpose: "Transactional email for notifications (MAIL_* in .env)"
    icon: "mail"
    cost: "Variable / free tier on many providers"
  - name: "Railway / Render / Fly.io (optional)"
    purpose: "PaaS deploy using docker/Dockerfile and injected env vars per docker/README.md"
    icon: "cloud"
    cost: "Platform-dependent (placeholder)"
  - name: "Local filesystem"
    purpose: "FILE_UPLOAD_DIR for receipt attachments (not S3 yet)"
    icon: "folder"
    cost: "$0"
  - name: "GitHub Actions (placeholder)"
    purpose: "CI build and test — not configured in repo yet"
    icon: "ci"
    cost: "Free for public repos"

deploymentLayers:
  - name: "Clients"
    color: "#4F46E5"
    components:
      - name: "Employee web / mobile app"
        icon: "smartphone"
        description: "Submits expenses and uploads receipts (future or separate frontend)"
      - name: "Manager dashboard"
        icon: "layout"
        description: "Approves/rejects queue, views summaries"
      - name: "Admin console"
        icon: "settings"
        description: "Settings and dashboard stats"

  - name: "Application runtime"
    color: "#059669"
    components:
      - name: "Spring Boot JAR"
        icon: "coffee"
        description: "company-expenses-api boot jar on port 8080"
      - name: "Docker container (app)"
        icon: "docker"
        description: "Built from docker/Dockerfile via compose"
      - name: "Bucket4j interceptor"
        icon: "gauge"
        description: "Global rate limit on incoming HTTP"

  - name: "Data layer"
    color: "#DC2626"
    components:
      - name: "PostgreSQL 15"
        icon: "database"
        description: "Local container in compose.local; external host in prod"
      - name: "Flyway"
        icon: "migrate"
        description: "Schema versioning at startup"
      - name: "Upload volume"
        icon: "folder"
        description: "Host path FILE_UPLOAD_DIR for attachments"

  - name: "Integrations"
    color: "#D97706"
    components:
      - name: "SMTP"
        icon: "mail"
        description: "spring.mail.* from environment"
      - name: "OpenAPI / Swagger"
        icon: "book"
        description: "Public documentation endpoints"

dockerFiles:
  - service: "docker-compose.local.yml"
    description: "Local development: Spring Boot app + PostgreSQL 15 with healthcheck and named volume."
    content: |
      services:
        app:
          build:
            context: ..
            dockerfile: docker/Dockerfile
          ports:
            - "8080:8080"
          depends_on:
            db:
              condition: service_healthy
          env_file:
            - ../.env
          environment:
            DB_HOST: db
            DB_PORT: "5432"
        db:
          image: postgres:15
          ports:
            - "5431:5432"
      volumes:
        postgres_data:

  - service: "docker-compose.prod.yml"
    description: "Production profile: app container only; DB_HOST points to external Postgres."
    content: |
      services:
        app:
          build:
            context: ..
            dockerfile: docker/Dockerfile
          ports:
            - "8080:8080"
          env_file:
            - ../.env
          restart: unless-stopped

  - service: "Dockerfile"
    description: "Multi-stage: Gradle 8.5 JDK 17 build (skip tests), Temurin 17 JRE runs app.jar."
    content: |
      FROM gradle:8.5-jdk17 AS build
      WORKDIR /app
      COPY . .
      RUN gradle build -x test
      FROM eclipse-temurin:17-jre-jammy
      COPY --from=build /app/build/libs/*.jar app.jar
      EXPOSE 8080
      ENTRYPOINT ["java", "-jar", "app.jar"]
---

> **Deploy story:** From repo root, `docker compose --env-file .env -f docker/docker-compose.local.yml up --build` for full stack; prod uses `docker-compose.prod.yml` with `DB_HOST` set to your cloud Postgres hostname.

> **Gradle bootRun:** Loads root `.env` in `bootRun` task; use `DB_PORT=5431` when Postgres runs via local compose.

> **Not containerized:** Redis/Celery from template projects—this API uses in-memory Bucket4j and Caffeine cache starter without external Redis in compose.

> **Production checklist:** Set strong `JWT_SECRET_KEY` (Base64 HMAC key), rotate DB passwords, mount persistent volume for `FILE_UPLOAD_DIR`, restrict Swagger in prod if desired, add CI pipeline placeholder.
