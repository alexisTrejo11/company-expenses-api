# Docker

This folder contains the Docker setup for **company-expenses-api**.

## Prerequisites

1. Copy the environment template from the project root:

   ```bash
   cp .env.example .env
   ```

2. Fill in `.env` at the **project root** (see `.env.example`).

## Important: run from the project root

Compose files live in `docker/` and reference `../.env` (repo root). Run all commands from the project root.

For **local** compose, `${DB_*}` in the Postgres service also needs the root `.env` for variable substitution:

```bash
docker compose --env-file .env -f docker/docker-compose.yml <command>
```

Production and other commands only need:

```bash
docker compose -f docker/docker-compose.<profile>.yml <command>
```

## Files

| File | Purpose |
|---|---|
| `../Dockerfile` | Multi-stage build: Gradle compile + Eclipse Temurin 17 JRE |
| `../docker-compose.yml` | Local development — app + PostgreSQL |
| `docker-compose.prod.yml` | Production / cloud deploy — app only, external database |

## Local (app + PostgreSQL)

Runs the API and PostgreSQL 15. The app overrides `DB_HOST=db` and `DB_PORT=5432` so it connects to the Postgres container on the Docker network (your root `.env` can keep `DB_HOST=localhost` for Gradle `bootRun`).

- App: [http://localhost:8080](http://localhost:8080)
- PostgreSQL (host access): `localhost:5431`

```bash
docker compose --env-file .env -f docker/docker-compose.yml up --build
```

Detached:

```bash
docker compose --env-file .env -f docker/docker-compose.yml up --build -d
```

Stop:

```bash
docker compose --env-file .env -f docker/docker-compose.yml down
```

Remove containers and the Postgres volume:

```bash
docker compose --env-file .env -f docker/docker-compose.yml down -v
```

### Local `.env` variables

| Variable | Example | Notes |
|---|---|---|
| `DB_NAME` | `company_expenses` | Used by app and Postgres container |
| `DB_USERNAME` | `postgres` | |
| `DB_PASSWORD` | `changeme` | |
| `DB_HOST` | `localhost` | Overridden to `db` inside the app container |
| `DB_PORT` | `5431` | Overridden to `5432` inside the app container; `5431` is for host access to Postgres |

## Production (app only)

No database container. Set your external Postgres host in `.env`:

| Variable | Example |
|---|---|
| `DB_HOST` | `your-db-host.example.com` |
| `DB_PORT` | `5432` |
| `DB_NAME` | `company_expenses` |
| `DB_USERNAME` | `postgres` |
| `DB_PASSWORD` | `your-secure-password` |

All variables from `.env.example` (`JWT_SECRET_KEY`, mail, etc.) are loaded via `env_file` — no extra `environment` block is required.

```bash
docker compose -f docker/docker-compose.prod.yml up --build -d
```

Stop:

```bash
docker compose -f docker/docker-compose.prod.yml down
```

On Railway, Render, Fly.io, etc., set `dockerfile` to `../Dockerfile`, build context to the repo root, and inject the same `DB_*` and app variables via the platform UI.
