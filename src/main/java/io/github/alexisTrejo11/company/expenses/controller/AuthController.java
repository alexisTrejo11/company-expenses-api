package io.github.alexisTrejo11.company.expenses.controller;

import io.github.alexisTrejo11.company.expenses.service.AuthService;
import io.github.alexisTrejo11.company.expenses.service.UserService;
import io.github.alexisTrejo11.company.expenses.shared.ResponseWrapper;
import io.github.alexisTrejo11.company.expenses.shared.Result;
import io.github.alexisTrejo11.company.expenses.shared.dto.auth.LoginDTO;
import io.github.alexisTrejo11.company.expenses.shared.dto.auth.UserInsertDTO;
import io.github.alexisTrejo11.company.expenses.shared.dto.user.UserDTO;
import io.github.alexisTrejo11.company.expenses.shared.enums.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/auth")
public class AuthController {

  private final UserService userService;
  private final AuthService authService;

  @Operation(summary = "Register an employee",
      description = "Registers a new employee and returns a JWT token")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "user successfully registered"),
      @ApiResponse(responseCode = "400", description = "Bad Request: Invalid input or credentials")
  })
  @PostMapping("/register-employee")
  public ResponseEntity<ResponseWrapper<String>> registerEmployee(@Valid @RequestBody UserInsertDTO userInsertDTO) {
    Result<Void> credentialsResult = authService.validateRegisterCredentials(userInsertDTO);
    if (!credentialsResult.isSuccess()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseWrapper.badRequest(credentialsResult.getErrorMessage()));
    }

    UserDTO userDTO = userService.createUser(userInsertDTO, Role.EMPLOYEE);

    String JWT = authService.ProcessRegister(userDTO);

    return ResponseEntity.ok(ResponseWrapper.success(JWT, "user With Role [" + Role.EMPLOYEE.name() + "] " +
        "Successfully Registered"));
  }

  @Operation(summary = "Register a manager",
      description = "Registers a new manager and returns a JWT token")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "user successfully registered"),
      @ApiResponse(responseCode = "400", description = "Bad Request: Invalid input or credentials")
  })
  @PostMapping("/register-manager")
  public ResponseEntity<ResponseWrapper<String>> registerManager(@Valid @RequestBody UserInsertDTO userInsertDTO) {
    Result<Void> credentialsResult = authService.validateRegisterCredentials(userInsertDTO);
    if (!credentialsResult.isSuccess()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseWrapper.badRequest(credentialsResult.getErrorMessage()));
    }

    UserDTO userDTO = userService.createUser(userInsertDTO, Role.MANAGER);

    String JWT = authService.ProcessRegister(userDTO);

    return ResponseEntity.ok(ResponseWrapper.success(JWT, "user With Role [" + Role.MANAGER.name() + "] " +
        "Successfully Registered"));
  }

  @Operation(summary = "Register an admin",
      description = "Registers a new admin and returns a JWT token")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "user successfully registered"),
      @ApiResponse(responseCode = "400", description = "Bad Request: Invalid input or credentials")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/register-admin")
  public ResponseEntity<ResponseWrapper<String>> registerAdmin(@Valid @RequestBody UserInsertDTO userInsertDTO) {
    Result<Void> credentialsResult = authService.validateRegisterCredentials(userInsertDTO);
    if (!credentialsResult.isSuccess()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseWrapper.badRequest(credentialsResult.getErrorMessage()));
    }

    UserDTO userDTO = userService.createUser(userInsertDTO, Role.ADMIN);

    String JWT = authService.ProcessRegister(userDTO);

    return ResponseEntity.ok(ResponseWrapper.success(JWT, "user With Role [" + Role.ADMIN.name() + "] " +
        "Successfully Registered"));
  }

  @Operation(summary = "Login user",
      description = "Logs in a user and returns a JWT token")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Login successfully completed"),
      @ApiResponse(responseCode = "400", description = "Bad Request: Invalid login credentials")
  })
  @PostMapping("/login")
  public ResponseEntity<ResponseWrapper<String>> login(@Valid @RequestBody LoginDTO loginDTO) {
    Result<UserDTO> credentialsResult = authService.validateLoginCredentials(loginDTO);
    if (!credentialsResult.isSuccess()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(ResponseWrapper.badRequest(credentialsResult.getErrorMessage()));
    }

    String JWT = authService.processLogin(credentialsResult.getData());

    return ResponseEntity.ok(ResponseWrapper.success(JWT, "Login Successfully Completed"));
  }
}
