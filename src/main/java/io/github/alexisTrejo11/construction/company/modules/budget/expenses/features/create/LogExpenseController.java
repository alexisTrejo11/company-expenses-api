package io.github.alexisTrejo11.construction.company.modules.budget.expenses.features.create;

import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/budgets/{budgetId}/expenses")
@RequiredArgsConstructor
@Tag(name = "Budget Expenses", description = "Expense execution and tracking")
public class LogExpenseController {
  private final LogExpenseHandler handler;

  @PostMapping
  @Operation(summary = "Log budget expense", description = "Logs an actual executed expense (linked to a line item and invoice).")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Expense created")
  })
  public ResponseEntity<ResponseWrapper<?>> createBudgetExpense(
      @PathVariable Long budgetId,
      @RequestBody @Valid LogExpenseCommand request) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ResponseWrapper.created(handler.execute(budgetId, request), "Expense"));
  }
}
