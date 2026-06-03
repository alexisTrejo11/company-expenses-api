---
projectId: "company-expenses-api"
featured: true
name: "Company Expenses API"
language: "Java"
category: "backend"
framework: "Spring Boot"
version: "1.0.0"
repositoryUrl: "https://github.com/alexisTrejo11/company-expenses-api"
liveDemoUrl: "http://localhost:8080/swagger-ui.html"
description: "REST API for corporate expense reporting: employees submit claims with receipts, managers approve or reject, finance processes reimbursements, and admins configure company settings—with JWT security, Flyway migrations, and Docker deploy."
techStack:
  - "Java 17"
  - "Spring Boot 3.4.1"
  - "Spring Security (OAuth2 Resource Server + JWT)"
  - "Spring Data JPA"
  - "PostgreSQL 15"
  - "Flyway"
  - "MapStruct"
  - "Bucket4j (rate limiting)"
  - "Caffeine (Spring Cache)"
  - "Spring Mail + Thymeleaf"
  - "springdoc-openapi 2.8.3"
  - "Docker (Gradle multi-stage + Temurin 17 JRE)"
status: "stable"
createdAt: "2024-05-25T00:00:00.000Z"
updatedAt: "2026-06-03T00:00:00.000Z"
---

> **Portfolio:** Replace `liveDemoUrl` with your deployed host when publishing. OpenAPI title in code is still "Expense Tracking API" (see `OpenApiConfig`)—consider aligning branding with this metadata name.

> **Build note:** `build.gradle` uses `spring-ai-postgresml-spring-boot-starter` for PostgreSQL connectivity; verify an explicit `postgresql` JDBC driver is present for production if you drop that starter.
