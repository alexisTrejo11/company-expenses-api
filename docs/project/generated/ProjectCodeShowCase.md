# Code Showcase

## JWT issuance with role claims

JWTService encodes subject (email) and roles list for Spring Security resource server consumption.

**Category:** security | **Duration:** 3 min read | **Tags:** jwt, security, auth

### JWTService.java

**Path:** `src/main/java/.../service/JWTService.java`

Tokens include issuer, expiry, subject, and roles claim stripped of ROLE_ prefix in authorities mapping.

```java
public String generateToken(UserDetails userDetails) {
    Instant now = Instant.now();
    JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("alexisTrejo.expenses.tracking")
            .issuedAt(now)
            .expiresAt(now.plusMillis(jwtExpirationMs))
            .subject(userDetails.getUsername())
            .claim("roles", userDetails.getAuthorities().stream()
                    .map(auth -> auth.getAuthority().replace("ROLE_", ""))
                    .toList())
            .build();
    return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
}
```

## Path-based security rules

SecurityConfig declares public auth/swagger routes and role requirements per API prefix.

**Category:** security | **Duration:** 4 min read | **Tags:** spring-security, rbac

### SecurityConfig.java

**Path:** `src/main/java/.../config/security/SecurityConfig.java`

Note FINANCIAL vs Role.FINANCE mismatch for reimbursement routes.

```java
.authorizeHttpRequests(auth -> auth
    .requestMatchers("/v1/api/auth/**", "/swagger-ui/**", "/api-docs/**").permitAll()
    .requestMatchers("/v1/api/admin/**").hasRole("ADMIN")
    .requestMatchers("/v1/api/employees/**").authenticated()
    .requestMatchers("/v1/api/manager/**").hasAnyRole("MANAGER", "ADMIN")
    .requestMatchers("/v1/api/reimbursements/**").hasAnyRole("MANAGER", "FINANCIAL")
    .anyRequest().authenticated())
.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt
    .decoder(jwtDecoder)
    .jwtAuthenticationConverter(jwtAuthenticationConverter())));
```

## Standard API response envelope

ResponseWrapper factory methods keep HTTP payloads consistent for clients.

**Category:** api | **Duration:** 2 min read | **Tags:** api-design, json

### ResponseWrapper.java

**Path:** `src/main/java/.../shared/ResponseWrapper.java`

Fields: success, data, message, code, time_stamp.

```java
public static <T> ResponseWrapper<T> success(T data, String message) {
    return new ResponseWrapper<>(
            true,
            data,
            message,
            HttpStatus.OK.value(),
            LocalDateTime.now()
    );
}
```

## Global Bucket4j rate limiting

Every request consumes one token; exhaustion returns 429 JSON.

**Category:** performance | **Duration:** 3 min read | **Tags:** bucket4j, interceptor

### RateLimitInterceptor.java

**Path:** `src/main/java/.../config/rateLimiter/RateLimitInterceptor.java`

Adds X-Rate-Limit-Remaining or X-Rate-Limit-Retry-After-Seconds headers.

```java
ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
if (!probe.isConsumed()) {
    response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
    response.getWriter().write(
        "{ \"error\": \"You have exhausted your API Request Quota\", ... }");
    return false;
}
```

## Submit expense with validation

EmployeeExpenseController validates DTO, creates PENDING expense, notifies stakeholders.

**Category:** api | **Duration:** 4 min read | **Tags:** expenses, workflow

### EmployeeExpenseController.java

**Path:** `src/main/java/.../controller/EmployeeExpenseController.java`

Email taken from JWT via JWTService.getEmailFromRequest.

```java
@PostMapping
public ResponseEntity<ResponseWrapper<ExpenseDTO>> requestExpense(
        @Valid @RequestBody ExpenseInsertDTO insertDTO,
        HttpServletRequest request) {
    String email = jwtService.getEmailFromRequest(request);
    Result<Void> validationResult = expenseService.validate(insertDTO);
    if (!validationResult.isSuccess()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseWrapper.badRequest(validationResult.getErrorMessage()));
    }
    ExpenseDTO expenseDTO = expenseService.createExpense(insertDTO, email, ExpenseStatus.PENDING);
    notificationService.sendNotificationFromExpense(expenseDTO);
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseWrapper.created(expenseDTO, "Expense"));
}
```

## Additional notes

> **Reading order:** JWT → Security rules → ResponseWrapper → rate limit → expense submit flow.

> **Extend showcase with:** MapStruct mapper example, Flyway V1 excerpt, and integration test snippet from `ExpenseAttachmentControllerTest`.

> **Placeholder:** Add frontend fetch example calling `/v1/api/auth/login` and attaching Bearer token—when UI repo exists.

