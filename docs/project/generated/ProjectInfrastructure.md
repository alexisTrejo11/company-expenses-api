# Infrastructure

## Metrics

| Label | Value | Description |
| --- | --- | --- |
| API port | 8080 | server.port in application.properties; exposed in Docker compose |
| Local Postgres host port | 5431 | Maps container 5432 for host access during Gradle bootRun |
| Java runtime | 17 | Eclipse Temurin JRE in production image |
| Flyway migrations | 2 | V1 schema + V2 demo seed |

## Cloud services

| Service | Purpose | Est. cost |
| --- | --- | --- |
| PostgreSQL (managed or self-hosted) | Primary datastore for users, expenses, reimbursements, notifications, admin settings | ~$0 local Docker — ~$15–50/mo managed (placeholder) |
| SMTP provider | Transactional email for notifications (MAIL_* in .env) | Variable / free tier on many providers |
| Railway / Render / Fly.io (optional) | PaaS deploy using docker/Dockerfile and injected env vars per docker/README.md | Platform-dependent (placeholder) |
| Local filesystem | FILE_UPLOAD_DIR for receipt attachments (not S3 yet) | $0 |
| GitHub Actions (placeholder) | CI build and test — not configured in repo yet | Free for public repos |

## Deployment layers

### Clients

- **Employee web / mobile app** — Submits expenses and uploads receipts (future or separate frontend)
- **Manager dashboard** — Approves/rejects queue, views summaries
- **Admin console** — Settings and dashboard stats

### Application runtime

- **Spring Boot JAR** — company-expenses-api boot jar on port 8080
- **Docker container (app)** — Built from docker/Dockerfile via compose
- **Bucket4j interceptor** — Global rate limit on incoming HTTP

### Data layer

- **PostgreSQL 15** — Local container in compose.local; external host in prod
- **Flyway** — Schema versioning at startup
- **Upload volume** — Host path FILE_UPLOAD_DIR for attachments

### Integrations

- **SMTP** — spring.mail.* from environment
- **OpenAPI / Swagger** — Public documentation endpoints

## Docker configuration

### docker-compose.local.yml

Local development: Spring Boot app + PostgreSQL 15 with healthcheck and named volume.

```yaml
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
```

### docker-compose.prod.yml

Production profile: app container only; DB_HOST points to external Postgres.

```yaml
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
```

### Dockerfile

Multi-stage: Gradle 8.5 JDK 17 build (skip tests), Temurin 17 JRE runs app.jar.

```yaml
FROM gradle:8.5-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle build -x test
FROM eclipse-temurin:17-jre-jammy
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

## Additional notes

> **Deploy story:** From repo root, `docker compose --env-file .env -f docker/docker-compose.local.yml up --build` for full stack; prod uses `docker-compose.prod.yml` with `DB_HOST` set to your cloud Postgres hostname.

> **Gradle bootRun:** Loads root `.env` in `bootRun` task; use `DB_PORT=5431` when Postgres runs via local compose.

> **Not containerized:** Redis/Celery from template projects—this API uses in-memory Bucket4j and Caffeine cache starter without external Redis in compose.

> **Production checklist:** Set strong `JWT_SECRET_KEY` (Base64 HMAC key), rotate DB passwords, mount persistent volume for `FILE_UPLOAD_DIR`, restrict Swagger in prod if desired, add CI pipeline placeholder.

