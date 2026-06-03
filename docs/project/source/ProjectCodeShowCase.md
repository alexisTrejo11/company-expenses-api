---
codeExamples:
  - id: "jwt-generation"
    title: "JWT issuance with role claims"
    description: "JWTService encodes subject (email) and roles list for Spring Security resource server consumption."
    category: "security"
    duration: "3 min read"
    views: 0
    tags:
      - "jwt"
      - "security"
      - "auth"
    files:
      - name: "JWTService.java"
        path: "src/main/java/.../service/JWTService.java"
        language: "java"
        highlighted: true
        explanation: "Tokens include issuer, expiry, subject, and roles claim stripped of ROLE_ prefix in authorities mapping."
        content: |
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

  - id: "security-filter-chain"
    title: "Path-based security rules"
    description: "SecurityConfig declares public auth/swagger routes and role requirements per API prefix."
    category: "security"
    duration: "4 min read"
    views: 0
    tags:
      - "spring-security"
      - "rbac"
    files:
      - name: "SecurityConfig.java"
        path: "src/main/java/.../config/security/SecurityConfig.java"
        language: "java"
        highlighted: true
        explanation: "Note FINANCIAL vs Role.FINANCE mismatch for reimbursement routes."
        content: |
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

  - id: "response-wrapper"
    title: "Standard API response envelope"
    description: "ResponseWrapper factory methods keep HTTP payloads consistent for clients."
    category: "api"
    duration: "2 min read"
    views: 0
    tags:
      - "api-design"
      - "json"
    files:
      - name: "ResponseWrapper.java"
        path: "src/main/java/.../shared/ResponseWrapper.java"
        language: "java"
        highlighted: true
        explanation: "Fields: success, data, message, code, time_stamp."
        content: |
          public static <T> ResponseWrapper<T> success(T data, String message) {
              return new ResponseWrapper<>(
                      true,
                      data,
                      message,
                      HttpStatus.OK.value(),
                      LocalDateTime.now()
              );
          }

  - id: "rate-limit-interceptor"
    title: "Global Bucket4j rate limiting"
    description: "Every request consumes one token; exhaustion returns 429 JSON."
    category: "performance"
    duration: "3 min read"
    views: 0
    tags:
      - "bucket4j"
      - "interceptor"
    files:
      - name: "RateLimitInterceptor.java"
        path: "src/main/java/.../config/rateLimiter/RateLimitInterceptor.java"
        language: "java"
        highlighted: true
        explanation: "Adds X-Rate-Limit-Remaining or X-Rate-Limit-Retry-After-Seconds headers."
        content: |
          ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
          if (!probe.isConsumed()) {
              response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
              response.getWriter().write(
                  "{ \"error\": \"You have exhausted your API Request Quota\", ... }");
              return false;
          }

  - id: "employee-expense-create"
    title: "Submit expense with validation"
    description: "EmployeeExpenseController validates DTO, creates PENDING expense, notifies stakeholders."
    category: "api"
    duration: "4 min read"
    views: 0
    tags:
      - "expenses"
      - "workflow"
    files:
      - name: "EmployeeExpenseController.java"
        path: "src/main/java/.../controller/EmployeeExpenseController.java"
        language: "java"
        highlighted: true
        explanation: "Email taken from JWT via JWTService.getEmailFromRequest."
        content: |
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
---

> **Reading order:** JWT → Security rules → ResponseWrapper → rate limit → expense submit flow.

> **Extend showcase with:** MapStruct mapper example, Flyway V1 excerpt, and integration test snippet from `ExpenseAttachmentControllerTest`.

> **Placeholder:** Add frontend fetch example calling `/v1/api/auth/login` and attaching Bearer token—when UI repo exists.
