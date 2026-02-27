package io.github.alexisTrejo11.company.expenses.controller;

import io.github.alexisTrejo11.company.expenses.service.JWTService;
import io.github.alexisTrejo11.company.expenses.service.NotificationService;
import io.github.alexisTrejo11.company.expenses.service.ReimbursementService;
import io.github.alexisTrejo11.company.expenses.shared.ResponseWrapper;
import io.github.alexisTrejo11.company.expenses.shared.Result;
import io.github.alexisTrejo11.company.expenses.shared.dto.reimbursement.ReimbursementDTO;
import io.github.alexisTrejo11.company.expenses.shared.dto.reimbursement.ReimbursementInsertDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/reimbursements")
@RequiredArgsConstructor
public class ReimbursementController {

  private final ReimbursementService reimbursementService;
  private final NotificationService notificationService;
  private final JWTService jwtService;


  @Operation(summary = "Get Reimbursements by user ID", description = "Retrieve all reimbursements for a specific user.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Reimbursements successfully fetched."),
      @ApiResponse(responseCode = "404", description = "No reimbursements found for the user.")
  })
  @GetMapping("/user/{userId}")
  public ResponseEntity<ResponseWrapper<Page<ReimbursementDTO>>> getReimbursementByUserId(@PathVariable Long userId,
                                                                                          @RequestParam(defaultValue = "0") int page,
                                                                                          @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<ReimbursementDTO> reimbursementPage = reimbursementService.getReimbursementByUserId(userId, pageable);

    return ResponseEntity.ok(ResponseWrapper.found(reimbursementPage, "reimbursement", "userId", userId));
  }

  @Operation(summary = "Get reimbursement by ID", description = "Retrieve a reimbursement by its unique ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "reimbursement successfully fetched."),
      @ApiResponse(responseCode = "404", description = "reimbursement not found.")
  })
  @GetMapping("/{reimbursementId}")
  public ResponseEntity<ResponseWrapper<ReimbursementDTO>> getReimbursementById(@PathVariable Long id) {
    ReimbursementDTO reimbursement = reimbursementService.getReimbursementById(id);
    if (reimbursement == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseWrapper.notFound("reimbursement", "ID", id));
    }

    return ResponseEntity.ok(ResponseWrapper.found(reimbursement, "reimbursement", "ID", id));
  }

  @Operation(summary = "Create reimbursement", description = "Create a new reimbursement.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "reimbursement successfully created."),
      @ApiResponse(responseCode = "400", description = "Invalid input data."),
      @ApiResponse(responseCode = "401", description = "Unauthorized user.")
  })
  @PostMapping
  public ResponseEntity<ResponseWrapper<Void>> createReimbursement(@Valid @RequestBody ReimbursementInsertDTO reimbursementInsertDTO,
                                                                   HttpServletRequest request) {
    String email = jwtService.getEmailFromRequest(request);

    Result<Void> validationResult = reimbursementService.validate(reimbursementInsertDTO);
    if (!validationResult.isSuccess()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(ResponseWrapper.badRequest(validationResult.getErrorMessage()));
    }

    ReimbursementDTO reimbursement = reimbursementService.createReimbursement(reimbursementInsertDTO, email);
    notificationService.sendNotificationFromExpense(reimbursement.getExpense());

    return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.created("reimbursement"));
  }
}
