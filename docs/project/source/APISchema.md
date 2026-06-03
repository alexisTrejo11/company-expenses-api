---
type: "REST"

httpEndpoints:
  - id: "openapi-docs"
    method: "GET"
    urlPath: "/swagger-ui.html"
    summary: "Swagger UI"
    description: "Interactive OpenAPI documentation (springdoc). Also available under /swagger-ui/** and /v3/api-docs/**."
    tags: ["service"]
    authenticated: false
    rateLimit: "Global — 20 capacity, 10 tokens/min refill (Bucket4j on /**)"
    responses:
      - status: 200
        description: "HTML Swagger UI"
        example: null

  - id: "openapi-json"
    method: "GET"
    urlPath: "/api-docs"
    summary: "OpenAPI JSON"
    description: "Machine-readable API description (springdoc.api-docs.path)."
    tags: ["service"]
    authenticated: false
    rateLimit: "Global — 20 capacity, 10 tokens/min refill"
    responses:
      - status: 200
        description: "OpenAPI 3 document"
        example:
          openapi: "3.0.1"
          info:
            title: "Expense Tracking API"
            version: "1.0"

  - id: "auth-login"
    method: "POST"
    urlPath: "/v1/api/auth/login"
    summary: "Login and obtain JWT"
    description: "Validates email/password and returns a JWT string in ResponseWrapper.data."
    tags: ["auth"]
    authenticated: false
    rateLimit: "Global Bucket4j (per-request token)"
    requestBody:
      contentType: "application/json"
      schema:
        email: "string (required)"
        password: "string (required)"
      example:
        email: "employee@company.example.com"
        password: "SecurePass123!"
    responses:
      - status: 200
        description: "Login successful"
        example:
          success: true
          data: "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9..."
          message: "Login Successfully Completed"
          code: 200
          time_stamp: "2026-06-03T10:00:00"
      - status: 400
        description: "Invalid credentials"
        example:
          success: false
          message: "Invalid credentials"
          code: 400

  - id: "auth-register-employee"
    method: "POST"
    urlPath: "/v1/api/auth/register-employee"
    summary: "Register employee"
    description: "Creates a user with role EMPLOYEE and returns JWT on success."
    tags: ["auth"]
    authenticated: false
    rateLimit: "Global Bucket4j"
    requestBody:
      contentType: "application/json"
      schema:
        email: "string"
        password: "string"
        firstName: "string"
        lastName: "string"
        department: "string"
      example:
        email: "new.employee@company.example.com"
        password: "SecurePass123!"
        firstName: "Alex"
        lastName: "Trejo"
        department: "Engineering"
    responses:
      - status: 200
        description: "Registered"
        example:
          success: true
          message: "user With Role [EMPLOYEE] Successfully Registered"

  - id: "auth-register-manager"
    method: "POST"
    urlPath: "/v1/api/auth/register-manager"
    summary: "Register manager"
    description: "Creates a user with role MANAGER and returns JWT."
    tags: ["auth"]
    authenticated: false
    rateLimit: "Global Bucket4j"
    requestBody:
      contentType: "application/json"
      schema:
        email: "string"
        password: "string"
      example:
        email: "manager@company.example.com"
        password: "SecurePass123!"
    responses:
      - status: 200
        description: "Registered"
        example:
          success: true

  - id: "auth-register-admin"
    method: "POST"
    urlPath: "/v1/api/auth/register-admin"
    summary: "Register admin (admin only)"
    description: "Requires authenticated ADMIN (`@PreAuthorize`). Creates another ADMIN user."
    tags: ["auth"]
    authenticated: true
    rateLimit: "Global Bucket4j"
    requestBody:
      contentType: "application/json"
      schema:
        email: "string"
        password: "string"
      example:
        email: "admin2@company.example.com"
        password: "SecurePass123!"
    responses:
      - status: 200
        description: "Admin created"
        example:
          success: true
      - status: 403
        description: "Forbidden for non-admin callers"
        example:
          success: false
          code: 403

  - id: "users-my-profile"
    method: "GET"
    urlPath: "/v1/api/users/my-profile"
    summary: "Get authenticated user profile"
    description: "Resolves user from JWT subject (email) via JWTService."
    tags: ["users"]
    authenticated: true
    rateLimit: "Global Bucket4j"
    responses:
      - status: 200
        description: "Profile returned"
        example:
          success: true
          data:
            email: "employee@company.example.com"
            firstName: "Alex"
            lastName: "Trejo"
            role: "EMPLOYEE"
            department: "Engineering"

  - id: "employee-expenses-list"
    method: "GET"
    urlPath: "/v1/api/employees/expenses/by-user/{userId}"
    summary: "List my expenses (paginated)"
    description: "Uses JWT email from request; path userId is present in mapping but listing is scoped by token subject."
    tags: ["expenses"]
    authenticated: true
    rateLimit: "Global Bucket4j"
    parameters:
      - name: "userId"
        in: "path"
        type: "long"
        required: true
        description: "Path segment (implementation uses JWT email)"
        example: 1
      - name: "page"
        in: "query"
        type: "integer"
        required: false
        description: "Page index (default 0)"
        example: 0
      - name: "size"
        in: "query"
        type: "integer"
        required: false
        description: "Page size (default 10)"
        example: 10
    responses:
      - status: 200
        description: "Page of ExpenseDTO"
        example:
          success: true
          data:
            content: []
            totalElements: 0

  - id: "employee-expenses-create"
    method: "POST"
    urlPath: "/v1/api/employees/expenses"
    summary: "Submit new expense"
    description: "Creates expense in PENDING status for authenticated employee; triggers notification."
    tags: ["expenses"]
    authenticated: true
    rateLimit: "Global Bucket4j"
    requestBody:
      contentType: "application/json"
      schema:
        amount: "number"
        category: "TRAVEL | FOOD | OFFICE_SUPPLIES | SOFTWARE_LICENSES | ENTERTAINMENT"
        description: "string"
        date: "date"
        receiptUrl: "string"
      example:
        amount: 42.50
        category: "TRAVEL"
        description: "Client visit taxi"
        date: "2026-06-01"
        receiptUrl: "https://example.com/receipt.pdf"
    responses:
      - status: 201
        description: "Expense created"
        example:
          success: true
          data:
            id: 10
            status: "PENDING"

  - id: "employee-expense-attachment-upload"
    method: "POST"
    urlPath: "/v1/api/employees/expenses/{expenseId}/attachments"
    summary: "Upload expense attachment"
    description: "Multipart file upload; stores file via FileHandler and links Attachment entity."
    tags: ["expenses"]
    authenticated: true
    rateLimit: "Global Bucket4j"
    parameters:
      - name: "expenseId"
        in: "path"
        type: "long"
        required: true
        description: "Expense ID"
        example: 10
    requestBody:
      contentType: "multipart/form-data"
      schema:
        file: "binary (required)"
      example:
        file: "(binary)"
    responses:
      - status: 200
        description: "Attachment added"
        example:
          success: true
          message: "Attachment Successfully Added to Expense With Id [10]"

  - id: "employee-expense-attachments-list"
    method: "GET"
    urlPath: "/v1/api/employees/expenses/{expenseId}/attachments"
    summary: "List attachments for expense"
    description: "Returns list of AttachmentDTO for the expense."
    tags: ["expenses"]
    authenticated: true
    rateLimit: "Global Bucket4j"
    parameters:
      - name: "expenseId"
        in: "path"
        type: "long"
        required: true
        description: "Expense ID"
        example: 10
    responses:
      - status: 200
        description: "Attachments list"
        example:
          success: true
          data: []

  - id: "manager-expense-by-id"
    method: "GET"
    urlPath: "/v1/api/manager/expenses/{expenseId}"
    summary: "Get expense by ID"
    description: "Requires MANAGER or ADMIN role."
    tags: ["manager"]
    authenticated: true
    rateLimit: "Global Bucket4j"
    parameters:
      - name: "expenseId"
        in: "path"
        type: "long"
        required: true
        description: "Expense ID"
        example: 10
    responses:
      - status: 200
        description: "ExpenseDTO"
        example:
          success: true
          data:
            id: 10
            status: "PENDING"
      - status: 404
        description: "Not found"
        example:
          success: false
          code: 404

  - id: "manager-expenses-by-user"
    method: "GET"
    urlPath: "/v1/api/manager/expenses/by-user/{userId}"
    summary: "List expenses by user email"
    description: "Paginated expenses for a user. Note: path variable is userId but handler binds email — verify client usage."
    tags: ["manager"]
    authenticated: true
    rateLimit: "Global Bucket4j"
    parameters:
      - name: "page"
        in: "query"
        type: "integer"
        required: false
        description: "Page index"
        example: 0
      - name: "size"
        in: "query"
        type: "integer"
        required: false
        description: "Page size"
        example: 10
    responses:
      - status: 200
        description: "Page of expenses"
        example:
          success: true

  - id: "manager-expenses-by-status"
    method: "GET"
    urlPath: "/v1/api/manager/expenses/by-status"
    summary: "List expenses by status"
    description: "Filters by ExpenseStatus; defaults to PENDING if status unknown. Sorts by createdAt ASC/DESC."
    tags: ["manager"]
    authenticated: true
    rateLimit: "Global Bucket4j"
    parameters:
      - name: "status"
        in: "query"
        type: "string"
        required: false
        description: "PENDING | APPROVED | REJECTED | REIMBURSED | MISCELLANEOUS"
        example: "PENDING"
      - name: "page"
        in: "query"
        type: "integer"
        required: false
        description: "Page index"
        example: 0
      - name: "size"
        in: "query"
        type: "integer"
        required: false
        description: "Page size"
        example: 10
      - name: "isSortedASC"
        in: "query"
        type: "boolean"
        required: false
        description: "Sort direction on createdAt"
        example: true
    responses:
      - status: 200
        description: "Filtered page"
        example:
          success: true

  - id: "manager-expense-summary"
    method: "GET"
    urlPath: "/v1/api/manager/expenses/s"
    summary: "Expense summary by date range"
    description: "Returns ExpenseSummary for optional startDate/endDate; defaults to current calendar month."
    tags: ["manager"]
    authenticated: true
    rateLimit: "Global Bucket4j"
    parameters:
      - name: "startDate"
        in: "query"
        type: "date-time"
        required: false
        description: "Range start"
        example: "2026-06-01T00:00:00"
      - name: "endDate"
        in: "query"
        type: "date-time"
        required: false
        description: "Range end"
        example: "2026-06-30T23:59:59"
    responses:
      - status: 200
        description: "Summary aggregates"
        example:
          success: true
          data:
            totalAmount: 1250.00
            summaryDateRange: "2026-06-01 to 2026-06-30"

  - id: "manager-expense-approve"
    method: "PUT"
    urlPath: "/v1/api/manager/expenses/{expenseId}/approve"
    summary: "Approve expense"
    description: "Sets approved status; records approver email from JWT. Mapping in code may be missing slash before {expenseId} — clients should test actual path."
    tags: ["manager"]
    authenticated: true
    rateLimit: "Global Bucket4j"
    parameters:
      - name: "expenseId"
        in: "path"
        type: "long"
        required: true
        description: "Expense ID"
        example: 10
    responses:
      - status: 200
        description: "Approved"
        example:
          success: true
          message: "Expense With Id 10 Successfully Approved"

  - id: "manager-expense-reject"
    method: "PUT"
    urlPath: "/v1/api/manager/expenses/{expenseId}/reject"
    summary: "Reject expense"
    description: "Requires ExpenseRejectDTO body with expenseId and rejection reason."
    tags: ["manager"]
    authenticated: true
    rateLimit: "Global Bucket4j"
    requestBody:
      contentType: "application/json"
      schema:
        expenseId: "long"
        rejectionReason: "string"
      example:
        expenseId: 10
        rejectionReason: "Missing itemized receipt"
    responses:
      - status: 200
        description: "Rejected"
        example:
          success: true

  - id: "manager-expense-delete"
    method: "DELETE"
    urlPath: "/v1/api/manager/expenses/{expenseId}"
    summary: "Soft delete expense"
    description: "Sets deleted_at on expense record."
    tags: ["manager"]
    authenticated: true
    rateLimit: "Global Bucket4j"
    parameters:
      - name: "expenseId"
        in: "path"
        type: "long"
        required: true
        description: "Expense ID"
        example: 10
    responses:
      - status: 200
        description: "Deleted"
        example:
          success: true
          message: "Expense deleted successfully"

  - id: "reimbursements-by-user"
    method: "GET"
    urlPath: "/v1/api/reimbursements/user/{userId}"
    summary: "List reimbursements for user"
    description: "Requires MANAGER or FINANCE role (see SecurityConfig vs Role enum alignment)."
    tags: ["reimbursements"]
    authenticated: true
    rateLimit: "Global Bucket4j"
    parameters:
      - name: "userId"
        in: "path"
        type: "long"
        required: true
        description: "User ID"
        example: 1
      - name: "page"
        in: "query"
        type: "integer"
        required: false
        description: "Page index"
        example: 0
      - name: "size"
        in: "query"
        type: "integer"
        required: false
        description: "Page size"
        example: 10
    responses:
      - status: 200
        description: "Page of ReimbursementDTO"
        example:
          success: true

  - id: "reimbursements-by-id"
    method: "GET"
    urlPath: "/v1/api/reimbursements/{reimbursementId}"
    summary: "Get reimbursement by ID"
    description: "Single reimbursement lookup."
    tags: ["reimbursements"]
    authenticated: true
    rateLimit: "Global Bucket4j"
    parameters:
      - name: "reimbursementId"
        in: "path"
        type: "long"
        required: true
        description: "Reimbursement ID"
        example: 5
    responses:
      - status: 200
        description: "ReimbursementDTO"
        example:
          success: true

  - id: "reimbursements-create"
    method: "POST"
    urlPath: "/v1/api/reimbursements"
    summary: "Create reimbursement"
    description: "Links reimbursement to expense; processed_by from JWT email."
    tags: ["reimbursements"]
    authenticated: true
    rateLimit: "Global Bucket4j"
    requestBody:
      contentType: "application/json"
      schema:
        expenseId: "long"
        reimbursementDate: "date"
      example:
        expenseId: 10
        reimbursementDate: "2026-06-15"
    responses:
      - status: 201
        description: "Created"
        example:
          success: true

  - id: "notifications-by-id"
    method: "GET"
    urlPath: "/v1/api/notifications/{notificationId}"
    summary: "Get notification"
    description: "Authenticated users (any role with valid JWT)."
    tags: ["notifications"]
    authenticated: true
    rateLimit: "Global Bucket4j"
    parameters:
      - name: "notificationId"
        in: "path"
        type: "long"
        required: true
        description: "Notification ID"
        example: 1
    responses:
      - status: 200
        description: "NotificationDTO"
        example:
          success: true

  - id: "notifications-by-user"
    method: "GET"
    urlPath: "/v1/api/notifications/by-user/{userId}"
    summary: "List notifications for user"
    description: "Paginated notifications."
    tags: ["notifications"]
    authenticated: true
    rateLimit: "Global Bucket4j"
    parameters:
      - name: "userId"
        in: "path"
        type: "long"
        required: true
        description: "User ID"
        example: 1
    responses:
      - status: 200
        description: "Page of notifications"
        example:
          success: true

  - id: "notifications-create"
    method: "POST"
    urlPath: "/v1/api/notifications"
    summary: "Create notification"
    description: "Manual notification insert (also used internally from expense events)."
    tags: ["notifications"]
    authenticated: true
    rateLimit: "Global Bucket4j"
    requestBody:
      contentType: "application/json"
      schema:
        userId: "long"
        type: "string"
        message: "string"
      example:
        userId: 1
        type: "EXPENSE_APPROVED"
        message: "Your expense was approved"
    responses:
      - status: 201
        description: "Created"
        example:
          success: true

  - id: "notifications-mark-read"
    method: "PUT"
    urlPath: "/v1/api/notifications/set-as-read/{notificationId}"
    summary: "Mark notification as read"
    description: "Updates read flag on notification."
    tags: ["notifications"]
    authenticated: true
    rateLimit: "Global Bucket4j"
    parameters:
      - name: "notificationId"
        in: "path"
        type: "long"
        required: true
        description: "Notification ID"
        example: 1
    responses:
      - status: 200
        description: "Marked read"
        example:
          success: true

  - id: "admin-dashboard"
    method: "GET"
    urlPath: "/v1/api/admin/dashboard"
    summary: "Admin dashboard"
    description: "Returns AdminDashboardDTO (stats). ADMIN role only."
    tags: ["admin"]
    authenticated: true
    rateLimit: "Global Bucket4j"
    responses:
      - status: 200
        description: "Dashboard payload"
        example:
          totalUsers: 12
          pendingExpenses: 4

  - id: "admin-settings-get"
    method: "GET"
    urlPath: "/v1/api/admin/settings"
    summary: "Get company settings"
    description: "Current SettingsDTO from AdminService."
    tags: ["admin"]
    authenticated: true
    rateLimit: "Global Bucket4j"
    responses:
      - status: 200
        description: "Settings"
        example:
          success: true
          data:
            companyName: "Acme Corp"

  - id: "admin-settings-update"
    method: "PUT"
    urlPath: "/v1/api/admin/settings"
    summary: "Update company settings"
    description: "Persists SettingsDTO."
    tags: ["admin"]
    authenticated: true
    rateLimit: "Global Bucket4j"
    requestBody:
      contentType: "application/json"
      schema:
        companyName: "string"
      example:
        companyName: "Acme Corp"
    responses:
      - status: 200
        description: "Updated"
        example:
          success: true
          message: "settings updated successfully"
---

> **Authentication:** Send `Authorization: Bearer <JWT>` for secured routes. Public: `/v1/api/auth/**`, Swagger, and `/api-docs`.

> **Response envelope:** Most endpoints use `ResponseWrapper` with `success`, `data`, `message`, `code`, `time_stamp`. Admin dashboard returns raw `AdminDashboardDTO` without wrapper.

> **Rate limiting:** Single global Bucket4j bucket (20 capacity, refill 10 per minute) on all paths via `RateLimitInterceptor`. HTTP 429 returns JSON with `retry_after_seconds`.

> **Missing / planned:** Spring Boot Actuator health endpoint; refresh-token or logout endpoints; OpenAPI security scheme documentation for Bearer JWT in springdoc config.
