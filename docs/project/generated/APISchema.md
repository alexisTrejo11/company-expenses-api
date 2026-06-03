# API Schema

**API type:** REST

## Admin

### `GET` /v1/api/admin/dashboard

**Admin dashboard**

Returns AdminDashboardDTO (stats). ADMIN role only.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Global Bucket4j |
| **Tags** | admin |

#### Responses

- **200** — Dashboard payload

```json
{
  "totalUsers": 12,
  "pendingExpenses": 4
}
```

---

### `GET` /v1/api/admin/settings

**Get company settings**

Current SettingsDTO from AdminService.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Global Bucket4j |
| **Tags** | admin |

#### Responses

- **200** — Settings

```json
{
  "success": true,
  "data": {
    "companyName": "Acme Corp"
  }
}
```

---

### `PUT` /v1/api/admin/settings

**Update company settings**

Persists SettingsDTO.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Global Bucket4j |
| **Tags** | admin |

#### Request body

**Content-Type:** `application/json`

**Schema (summary):**

```json
{
  "companyName": "string"
}
```

**Example:**

```json
{
  "companyName": "Acme Corp"
}
```

#### Responses

- **200** — Updated

```json
{
  "success": true,
  "message": "settings updated successfully"
}
```

---

## Auth

### `POST` /v1/api/auth/login

**Login and obtain JWT**

Validates email/password and returns a JWT string in ResponseWrapper.data.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Global Bucket4j (per-request token) |
| **Tags** | auth |

#### Request body

**Content-Type:** `application/json`

**Schema (summary):**

```json
{
  "email": "string (required)",
  "password": "string (required)"
}
```

**Example:**

```json
{
  "email": "employee@company.example.com",
  "password": "SecurePass123!"
}
```

#### Responses

- **200** — Login successful

```json
{
  "success": true,
  "data": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...",
  "message": "Login Successfully Completed",
  "code": 200,
  "time_stamp": "2026-06-03T10:00:00"
}
```

- **400** — Invalid credentials

```json
{
  "success": false,
  "message": "Invalid credentials",
  "code": 400
}
```

---

### `POST` /v1/api/auth/register-employee

**Register employee**

Creates a user with role EMPLOYEE and returns JWT on success.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Global Bucket4j |
| **Tags** | auth |

#### Request body

**Content-Type:** `application/json`

**Schema (summary):**

```json
{
  "email": "string",
  "password": "string",
  "firstName": "string",
  "lastName": "string",
  "department": "string"
}
```

**Example:**

```json
{
  "email": "new.employee@company.example.com",
  "password": "SecurePass123!",
  "firstName": "Alex",
  "lastName": "Trejo",
  "department": "Engineering"
}
```

#### Responses

- **200** — Registered

```json
{
  "success": true,
  "message": "user With Role [EMPLOYEE] Successfully Registered"
}
```

---

### `POST` /v1/api/auth/register-manager

**Register manager**

Creates a user with role MANAGER and returns JWT.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Global Bucket4j |
| **Tags** | auth |

#### Request body

**Content-Type:** `application/json`

**Schema (summary):**

```json
{
  "email": "string",
  "password": "string"
}
```

**Example:**

```json
{
  "email": "manager@company.example.com",
  "password": "SecurePass123!"
}
```

#### Responses

- **200** — Registered

```json
{
  "success": true
}
```

---

### `POST` /v1/api/auth/register-admin

**Register admin (admin only)**

Requires authenticated ADMIN (`@PreAuthorize`). Creates another ADMIN user.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Global Bucket4j |
| **Tags** | auth |

#### Request body

**Content-Type:** `application/json`

**Schema (summary):**

```json
{
  "email": "string",
  "password": "string"
}
```

**Example:**

```json
{
  "email": "admin2@company.example.com",
  "password": "SecurePass123!"
}
```

#### Responses

- **200** — Admin created

```json
{
  "success": true
}
```

- **403** — Forbidden for non-admin callers

```json
{
  "success": false,
  "code": 403
}
```

---

## Expenses

### `GET` /v1/api/employees/expenses/by-user/{userId}

**List my expenses (paginated)**

Uses JWT email from request; path userId is present in mapping but listing is scoped by token subject.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Global Bucket4j |
| **Tags** | expenses |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| userId | path | long | Yes | Path segment (implementation uses JWT email) |
| page | query | integer | No | Page index (default 0) |
| size | query | integer | No | Page size (default 10) |

#### Responses

- **200** — Page of ExpenseDTO

```json
{
  "success": true,
  "data": {
    "content": [],
    "totalElements": 0
  }
}
```

---

### `POST` /v1/api/employees/expenses

**Submit new expense**

Creates expense in PENDING status for authenticated employee; triggers notification.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Global Bucket4j |
| **Tags** | expenses |

#### Request body

**Content-Type:** `application/json`

**Schema (summary):**

```json
{
  "amount": "number",
  "category": "TRAVEL | FOOD | OFFICE_SUPPLIES | SOFTWARE_LICENSES | ENTERTAINMENT",
  "description": "string",
  "date": "date",
  "receiptUrl": "string"
}
```

**Example:**

```json
{
  "amount": 42.5,
  "category": "TRAVEL",
  "description": "Client visit taxi",
  "date": "2026-06-01",
  "receiptUrl": "https://example.com/receipt.pdf"
}
```

#### Responses

- **201** — Expense created

```json
{
  "success": true,
  "data": {
    "id": 10,
    "status": "PENDING"
  }
}
```

---

### `POST` /v1/api/employees/expenses/{expenseId}/attachments

**Upload expense attachment**

Multipart file upload; stores file via FileHandler and links Attachment entity.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Global Bucket4j |
| **Tags** | expenses |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| expenseId | path | long | Yes | Expense ID |

#### Request body

**Content-Type:** `multipart/form-data`

**Schema (summary):**

```json
{
  "file": "binary (required)"
}
```

**Example:**

```json
{
  "file": "(binary)"
}
```

#### Responses

- **200** — Attachment added

```json
{
  "success": true,
  "message": "Attachment Successfully Added to Expense With Id [10]"
}
```

---

### `GET` /v1/api/employees/expenses/{expenseId}/attachments

**List attachments for expense**

Returns list of AttachmentDTO for the expense.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Global Bucket4j |
| **Tags** | expenses |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| expenseId | path | long | Yes | Expense ID |

#### Responses

- **200** — Attachments list

```json
{
  "success": true,
  "data": []
}
```

---

## Manager

### `GET` /v1/api/manager/expenses/{expenseId}

**Get expense by ID**

Requires MANAGER or ADMIN role.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Global Bucket4j |
| **Tags** | manager |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| expenseId | path | long | Yes | Expense ID |

#### Responses

- **200** — ExpenseDTO

```json
{
  "success": true,
  "data": {
    "id": 10,
    "status": "PENDING"
  }
}
```

- **404** — Not found

```json
{
  "success": false,
  "code": 404
}
```

---

### `GET` /v1/api/manager/expenses/by-user/{userId}

**List expenses by user email**

Paginated expenses for a user. Note: path variable is userId but handler binds email — verify client usage.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Global Bucket4j |
| **Tags** | manager |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| page | query | integer | No | Page index |
| size | query | integer | No | Page size |

#### Responses

- **200** — Page of expenses

```json
{
  "success": true
}
```

---

### `GET` /v1/api/manager/expenses/by-status

**List expenses by status**

Filters by ExpenseStatus; defaults to PENDING if status unknown. Sorts by createdAt ASC/DESC.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Global Bucket4j |
| **Tags** | manager |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| status | query | string | No | PENDING | APPROVED | REJECTED | REIMBURSED | MISCELLANEOUS |
| page | query | integer | No | Page index |
| size | query | integer | No | Page size |
| isSortedASC | query | boolean | No | Sort direction on createdAt |

#### Responses

- **200** — Filtered page

```json
{
  "success": true
}
```

---

### `GET` /v1/api/manager/expenses/s

**Expense summary by date range**

Returns ExpenseSummary for optional startDate/endDate; defaults to current calendar month.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Global Bucket4j |
| **Tags** | manager |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| startDate | query | date-time | No | Range start |
| endDate | query | date-time | No | Range end |

#### Responses

- **200** — Summary aggregates

```json
{
  "success": true,
  "data": {
    "totalAmount": 1250.0,
    "summaryDateRange": "2026-06-01 to 2026-06-30"
  }
}
```

---

### `PUT` /v1/api/manager/expenses/{expenseId}/approve

**Approve expense**

Sets approved status; records approver email from JWT. Mapping in code may be missing slash before {expenseId} — clients should test actual path.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Global Bucket4j |
| **Tags** | manager |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| expenseId | path | long | Yes | Expense ID |

#### Responses

- **200** — Approved

```json
{
  "success": true,
  "message": "Expense With Id 10 Successfully Approved"
}
```

---

### `PUT` /v1/api/manager/expenses/{expenseId}/reject

**Reject expense**

Requires ExpenseRejectDTO body with expenseId and rejection reason.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Global Bucket4j |
| **Tags** | manager |

#### Request body

**Content-Type:** `application/json`

**Schema (summary):**

```json
{
  "expenseId": "long",
  "rejectionReason": "string"
}
```

**Example:**

```json
{
  "expenseId": 10,
  "rejectionReason": "Missing itemized receipt"
}
```

#### Responses

- **200** — Rejected

```json
{
  "success": true
}
```

---

### `DELETE` /v1/api/manager/expenses/{expenseId}

**Soft delete expense**

Sets deleted_at on expense record.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Global Bucket4j |
| **Tags** | manager |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| expenseId | path | long | Yes | Expense ID |

#### Responses

- **200** — Deleted

```json
{
  "success": true,
  "message": "Expense deleted successfully"
}
```

---

## Notifications

### `GET` /v1/api/notifications/{notificationId}

**Get notification**

Authenticated users (any role with valid JWT).

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Global Bucket4j |
| **Tags** | notifications |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| notificationId | path | long | Yes | Notification ID |

#### Responses

- **200** — NotificationDTO

```json
{
  "success": true
}
```

---

### `GET` /v1/api/notifications/by-user/{userId}

**List notifications for user**

Paginated notifications.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Global Bucket4j |
| **Tags** | notifications |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| userId | path | long | Yes | User ID |

#### Responses

- **200** — Page of notifications

```json
{
  "success": true
}
```

---

### `POST` /v1/api/notifications

**Create notification**

Manual notification insert (also used internally from expense events).

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Global Bucket4j |
| **Tags** | notifications |

#### Request body

**Content-Type:** `application/json`

**Schema (summary):**

```json
{
  "userId": "long",
  "type": "string",
  "message": "string"
}
```

**Example:**

```json
{
  "userId": 1,
  "type": "EXPENSE_APPROVED",
  "message": "Your expense was approved"
}
```

#### Responses

- **201** — Created

```json
{
  "success": true
}
```

---

### `PUT` /v1/api/notifications/set-as-read/{notificationId}

**Mark notification as read**

Updates read flag on notification.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Global Bucket4j |
| **Tags** | notifications |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| notificationId | path | long | Yes | Notification ID |

#### Responses

- **200** — Marked read

```json
{
  "success": true
}
```

---

## Reimbursements

### `GET` /v1/api/reimbursements/user/{userId}

**List reimbursements for user**

Requires MANAGER or FINANCE role (see SecurityConfig vs Role enum alignment).

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Global Bucket4j |
| **Tags** | reimbursements |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| userId | path | long | Yes | User ID |
| page | query | integer | No | Page index |
| size | query | integer | No | Page size |

#### Responses

- **200** — Page of ReimbursementDTO

```json
{
  "success": true
}
```

---

### `GET` /v1/api/reimbursements/{reimbursementId}

**Get reimbursement by ID**

Single reimbursement lookup.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Global Bucket4j |
| **Tags** | reimbursements |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| reimbursementId | path | long | Yes | Reimbursement ID |

#### Responses

- **200** — ReimbursementDTO

```json
{
  "success": true
}
```

---

### `POST` /v1/api/reimbursements

**Create reimbursement**

Links reimbursement to expense; processed_by from JWT email.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Global Bucket4j |
| **Tags** | reimbursements |

#### Request body

**Content-Type:** `application/json`

**Schema (summary):**

```json
{
  "expenseId": "long",
  "reimbursementDate": "date"
}
```

**Example:**

```json
{
  "expenseId": 10,
  "reimbursementDate": "2026-06-15"
}
```

#### Responses

- **201** — Created

```json
{
  "success": true
}
```

---

## Service

### `GET` /swagger-ui.html

**Swagger UI**

Interactive OpenAPI documentation (springdoc). Also available under /swagger-ui/** and /v3/api-docs/**.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Global — 20 capacity, 10 tokens/min refill (Bucket4j on /**) |
| **Tags** | service |

#### Responses

- **200** — HTML Swagger UI
---

### `GET` /api-docs

**OpenAPI JSON**

Machine-readable API description (springdoc.api-docs.path).

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Global — 20 capacity, 10 tokens/min refill |
| **Tags** | service |

#### Responses

- **200** — OpenAPI 3 document

```json
{
  "openapi": "3.0.1",
  "info": {
    "title": "Expense Tracking API",
    "version": "1.0"
  }
}
```

---

## Users

### `GET` /v1/api/users/my-profile

**Get authenticated user profile**

Resolves user from JWT subject (email) via JWTService.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Global Bucket4j |
| **Tags** | users |

#### Responses

- **200** — Profile returned

```json
{
  "success": true,
  "data": {
    "email": "employee@company.example.com",
    "firstName": "Alex",
    "lastName": "Trejo",
    "role": "EMPLOYEE",
    "department": "Engineering"
  }
}
```

---

## Additional notes

> **Authentication:** Send `Authorization: Bearer <JWT>` for secured routes. Public: `/v1/api/auth/**`, Swagger, and `/api-docs`.

> **Response envelope:** Most endpoints use `ResponseWrapper` with `success`, `data`, `message`, `code`, `time_stamp`. Admin dashboard returns raw `AdminDashboardDTO` without wrapper.

> **Rate limiting:** Single global Bucket4j bucket (20 capacity, refill 10 per minute) on all paths via `RateLimitInterceptor`. HTTP 429 returns JSON with `retry_after_seconds`.

> **Missing / planned:** Spring Boot Actuator health endpoint; refresh-token or logout endpoints; OpenAPI security scheme documentation for Bearer JWT in springdoc config.

