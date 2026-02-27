package io.github.alexisTrejo11.company.expenses.controller;

import io.github.alexisTrejo11.company.expenses.service.JWTService;
import io.github.alexisTrejo11.company.expenses.service.UserService;
import io.github.alexisTrejo11.company.expenses.shared.ResponseWrapper;
import io.github.alexisTrejo11.company.expenses.shared.Result;
import io.github.alexisTrejo11.company.expenses.shared.dto.user.ProfileDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/users")
@RequiredArgsConstructor
public class UserController {

  private final JWTService jwtService;
  private final UserService userService;

  @Operation(summary = "Get My Profile", description = "Retrieve the profile information of the authenticated user.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "user profile successfully fetched."),
      @ApiResponse(responseCode = "401", description = "Unauthorized user."),
      @ApiResponse(responseCode = "404", description = "user profile not found.")
  })
  @GetMapping("/my-profile")
  public ResponseEntity<ResponseWrapper<ProfileDTO>> getMyProfile(HttpServletRequest request) {
    String email = jwtService.getEmailFromRequest(request);

    Result<ProfileDTO> userResult = userService.getProfileById(email);
    if (!userResult.isSuccess()) {
      var errorMessage = userResult.getErrorMessage();
      return ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body(ResponseWrapper.error(errorMessage, 404));
    }

    return ResponseEntity.ok(ResponseWrapper.success(userResult.getData(), "user Profile Successfully Fetched"));
  }
}
