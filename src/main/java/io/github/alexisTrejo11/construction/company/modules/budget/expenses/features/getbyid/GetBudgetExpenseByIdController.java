package io.github.alexisTrejo11.construction.company.modules.budget.expenses.features.getbyid;

import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/budgets/{budgetId}/expenses")
@RequiredArgsConstructor
@Tag(name = "Budget Expenses", description = "Expense execution and tracking")
public class GetBudgetExpenseByIdController {
  private final GetBudgetExpenseByIdHandler handler;

  @GetMapping("/{expenseId}")
  @Operation(summary = "Get budget expense", description = "Fetches specific transaction details and receipt metadata.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Expense found")
  })
  public ResponseEntity<ResponseWrapper<?>> getBudgetExpense(
      @PathVariable Long budgetId,
      @PathVariable Long expenseId) {
    return ResponseEntity.ok(ResponseWrapper.found(
        handler.execute(new GetBudgetExpenseByIdQuery(budgetId, expenseId)),
        "Expense",
        "ID",
        expenseId
    ));
  }
}
