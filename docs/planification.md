# Consturction Company API Planification

> Planification of the Construction Company API Version 2.0.0

---

## 1. Purpose & Scope

**What does this API do, in one paragraph?**
*This API is a pure API for a construction company to manage their projects, users, expenses, projects, budgeting, contractors and approvals.*

**Who consumes it?**
*No one, it's a internal pure API*

**What's explicitly out of scope for v1?**
*Naming what you're NOT doing prevents scope creep during coding.*

---

## 2. Domain Model

**Core entities** — list every "thing" the system manages:

| Entity | Description | Key attributes |
|---|---|---|
| User | | id, email, ... |
| Project | | id, name, description, ... |
| Project Phase | | id, name, description, ... |
| Project Member | | id, name, description, ... |
| Expense | | id, name, description, ... |
| Budgeting | | id, name, description, ... |
| ... | | |

**Relationships between entities:**
*Example: "User 1—N Orders", "Order N—N Product (through OrderItem)"*

**Draw or describe it** — even ASCII is fine:
```
User ──1:N── Order ──1:N── OrderItem ──N:1── Product
```

---

## 3. Layered Architecture

#### **Modules List**

```
io/github/alexisTrejo11/construction/company/
├── modules/
│   ├── users/
│   ├── notifications/
│   ├── expenses/
│   ├── projects/
│   ├── budgeting/
│   ├── contractors/
│   ├── approvals/
│   └── shared/


```
**Modules description:**
- **users:** Auth, roles, permissions by project
- **notifications:** Alerts of cost overruns, approvals
- **expenses:** Records, invoices/tickets, categorization
- **projects:** Project management, phases and locations
- **budgeting:** Base budget, concepts and control of deviations
- **contractors:** Suppliers, subcontractors and estimates
- **approvals:** Rules engine and approval workflows

#### **Modules Structure**

```
├── domain/
│   ├── entities/
│   ├── repositories/
│   ├── services/
│   ├── dtos/
│   ├── exceptions/
│   ├── mappers/
│   ├── validators/
├── application/
│   ├── ports/
│   ├── services/
├── infrastructure/
│   ├── repository/
│   ├── web/
```

---

## 4. Endpoints (Resource Design)

#### User

| Method | Path  | Request body | Permission | Response | Status Code | Description |
|---|---|---|---|---|---|---|
| GET | users/me | — | Authenticate | PagedResponse<UserResponse> | 200 | Get the user profile |
| POST | users | CreateUserRequest | Admin | UserResponse | 201 | Create a new user |
| PUT | users/{id} | UpdateUserRequest | Admin | UserResponse | 200 | Update a user |
| PATCH | users/status/{id} | UpdateUserStatusRequest | Admin | UserResponse | 200 | Update a user status |
| PATCH | users/restore/{id} | — | Admin | UserResponse | 200 | Restore a user |
| DELETE | users/{id} | — | Admin | Void | 204 | Soft delete a user |

#### Authentication

| Method | Path  | Request body | Permission | Response | Status Code | Description |
|---|---|---|---|---|---|---|
| POST | auth/login | LoginRequest | Public | TokenResponse | 200 | Login a user |
| POST | auth/register | RegisterRequest | Public | UserResponse | 201 | Register a new user |
| POST | auth/refresh | RefreshTokenRequest | Public | TokenResponse | 200 | Refresh a token |
| POST | auth/logout | LogoutRequest | Public | Void | 200 | Logout a user |
| POST | auth/forgot-password | ForgotPasswordRequest | Public | Void | 200 | Forgot password |
| POST | auth/reset-password | ResetPasswordRequest | Public | Void | 200 | Reset password |

#### Project

> Permissions are the target contract (not wired on the controllers yet).

| Method | Path | Request body | Permission | Response | Status Code | Description |
|---|---|---|---|---|---|---|
| GET | projects?params=... | — | AdminOrStaff | PagedResponse<ProjectResponse> | 200 | Get projects with filters and pagination |
| POST | projects | CreateProjectRequest | Admin | ProjectResponse | 201 | Create a new project |
| GET | projects/my-projects | — | Authenticate | PagedResponse<ProjectResponse> | 200 | Get projects assigned to the current user |
| GET | projects/summary | — | AdminOrStaff | ProjectsGlobalSummaryResponse | 200 | Get a global summary of all projects |
| GET | projects/{id} | — | AdminOrStaff | ProjectResponse | 200 | Get a project by ID |
| GET | projects/code/{code} | — | AdminOrStaff | ProjectResponse | 200 | Get a project by code |
| GET | projects/{id}/summary | — | AdminOrStaff | ProjectSummaryResponse | 200 | Get a project summary by ID |
| PUT | projects/{id} | UpdateProjectRequest | Admin | ProjectResponse | 200 | Update a project |
| PATCH | projects/{id}/status | UpdateProjectStatusRequest | Admin | ProjectResponse | 200 | Update a project status |
| PATCH | projects/{id}/restore | — | Admin | ProjectResponse | 200 | Restore a project |
| DELETE | projects/{id} | — | Admin | Void | 204 | Soft delete a project |

#### Project Phases

| Method | Path | Request body | Permission | Response | Status Code | Description |
|---|---|---|---|---|---|---|
| GET | projects/{id}/phases | — | AdminOrStaff | List<ProjectPhaseResponse> | 200 | Get all phases of a project |
| POST | projects/{id}/phases | CreateProjectPhaseRequest | Admin | ProjectPhaseResponse | 201 | Add a phase to a project |
| GET | projects/{id}/phases/{phaseId} | — | AdminOrStaff | ProjectPhaseResponse | 200 | Get a project phase by ID |
| PUT | projects/{id}/phases/{phaseId} | CreateProjectPhaseRequest | Admin | ProjectPhaseResponse | 200 | Update a project phase |
| PATCH | projects/{id}/phases/reorder | ReorderPhasesRequest | Admin | Void | 200 | Reorder project phases |
| DELETE | projects/{id}/phases/{phaseId} | — | Admin | Void | 204 | Remove a phase from a project |

#### Project Members

| Method | Path | Request body | Permission | Response | Status Code | Description |
|---|---|---|---|---|---|---|
| GET | projects/{id}/members | — | AdminOrStaff | List<ProjectMemberResponse> | 200 | Get all members of a project |
| POST | projects/{id}/members | AddProjectMemberRequest | Admin | ProjectMemberResponse | 201 | Add a member to a project |
| PUT | projects/{id}/members/{userId} | UpdateProjectMemberRoleRequest | Admin | ProjectMemberResponse | 200 | Update a project member role |
| DELETE | projects/{id}/members/{userId} | — | Admin | Void | 204 | Remove a member from a project |

#### Budget

> Permissions are the target contract (not wired on the controllers yet).

| Method | Path | Request body | Permission | Response | Status Code | Description |
|---|---|---|---|---|---|---|
| POST | projects/{id}/budget | CreateBudgetRequest | Admin | BudgetResponse | 201 | Initialize a master budget for a project |
| GET | projects/{id}/budget | — | AdminOrStaff | BudgetResponse | 200 | Get the budget associated with a project |
| GET | budgets/{id} | — | AdminOrStaff | BudgetResponse | 200 | Get a budget by ID |
| PUT | budgets/{id} | UpdateBudgetRequest | Admin | BudgetResponse | 200 | Update estimated amount or currency |
| GET | budgets/{id}/summary | — | AdminOrStaff | BudgetSummaryResponse | 200 | Get financial metrics (estimated vs spent, variance, balance) |

#### Budget Items

| Method | Path | Request body | Permission | Response | Status Code | Description |
|---|---|---|---|---|---|---|
| GET | budgets/{id}/items?params=... | — | AdminOrStaff | PagedResponse<BudgetItemResponse> | 200 | List budget line items with filters and pagination |
| POST | budgets/{id}/items | CreateBudgetItemRequest | Admin | BudgetItemResponse | 201 | Add a planned line item to a budget |
| PUT | budgets/{id}/items/{itemId} | CreateBudgetItemRequest | Admin | BudgetItemResponse | 200 | Update a budget line item |
| DELETE | budgets/{id}/items/{itemId} | — | Admin | Void | 204 | Remove an unexecuted line item |

#### Budget Expenses

| Method | Path | Request body | Permission | Response | Status Code | Description |
|---|---|---|---|---|---|---|
| POST | budgets/{id}/expenses | LogExpenseRequest | AdminOrStaff | ExpenseResponse | 201 | Log an executed expense against a line item |
| GET | budgets/{id}/expenses?params=... | — | AdminOrStaff | PagedResponse<ExpenseResponse> | 200 | Search budget expenses with filters and pagination |
| GET | budgets/{id}/expenses/{expenseId} | — | AdminOrStaff | ExpenseResponse | 200 | Get a budget expense by ID |


**Pagination/filtering/sorting strategy:**
*Internally we use Spring Data `Pageable`, query params for filters*
*For the API, we use custom pagination and filtering schemas. Request and response return PageRequest and PagedResponse<T> respectively.*

**Versioning strategy:**
*e.g. URI versioning `/api/v2/...`, or header-based*

---

## 5. Persistence

**Database:** *PostgreSQL (Deploy)/ H2 (Test) / SQLite (Development)*

**ORM approach:** *Spring Data JPA + Hibernate*

**Migration tool:** *Flyway (Deploy)*

**Key constraints to define:**
*Unique constraints, foreign keys, cascade behavior (CascadeType, orphanRemoval), indexes for frequent queries*

---

## 6. Security & Auth

**Auth mechanism:** *JWT (Refresh and Access tokens)*

**Who can do what:** *Roles/permissions matrix (ADMIN, STAFF, USER)*

**Public vs protected endpoints:** *Mark from the table in section 4*

**Password/secret handling:** *BCrypt for passwords, secrets via env vars or vault — never hardcoded*

**Auth Implementation:**
*We will use Spring Security for authentication and authorization. We will use stateless authentication to change spring security default behavior.*
*A filter will be created to validate the JWT token and set the user details in the security context. A UserContext class will be created to store the user details in the security context.*
---

## 7. Error Handling

**Error response format example**

```json
{
  "message": "Email already exists",
  "timestamp": "2026-08-13T10:00:00Z",
  "error": {
    "code": "EMAIL_ALREADY_EXISTS_ERROR",
    "trace_id": "...",
    "path": "/api/users",
    "details": [
      {
        "field": "email",
        "message": "Email already exists"
      }
    ]
  }
}
```

**Error Handling Strategy:**

In this API, we will use two approaches to handle errors:
- **Result Patterns:** We will use a custom Result Patterns to handle errors. Also a class will be create top return the result of the operation. This approach will be used in the common and expected errors such as business rules errors, validation errors, etc. **For exceptional scenarios.** Imporving perfomance and reducing latency and perfomance.

- **Exceptions:** Custom exceptions, Jakarta Validation exceptions, Spring Boot exceptions, etc. A global exception handler will be created to catch exceptions. If some exepctions is unkwonwn, a generic exception will be returned. For exceptional scenarios.

**Global exception handler:** `@RestControllerAdvice` + `@ExceptionHandler` — list custom exceptions you'll need (e.g. `ResourceNotFoundException`, `DuplicateEmailException`)
---

## 8. Testing

**Testing Strategy:**

- **Integration tests:** *For each endpoint, a integration test will be created to test the endpoint. Following the all the worklow calling many endpoints as required. If is too complex, a mock will be created to test the endpoint. Will cover 100% of the endpoints with happy path and expected errors.*
- **Unit tests:** *Not unit tests will be created for this API, but we will use the integration tests to cover the code coverage.*
---

## 9. Infrastructure

**Infrastructure Strategy:**

- **Database:** *PostgreSQL (Deploy)/ H2 (Test) / SQLite (Development)*
- **ORM approach:** *Spring Data JPA + Hibernate*
- **Migration tool:** *Flyway (Deploy)*
- **Key constraints to define:** *Unique constraints, foreign keys, cascade behavior (CascadeType, orphanRemoval), indexes for frequent queries*
- **Cache:** *Redis (Deploy)*
-- **Docker:** *Docker (Deploy)*
- **CI/CD:** *GitHub Actions (Deploy)*
- **Monitoring:** *Prometheus (Deploy)*
- **Logging:** *Logstash (Deploy)*

---

## 10. Documentation

**API documentation:** *springdoc-openapi (Swagger UI)?*