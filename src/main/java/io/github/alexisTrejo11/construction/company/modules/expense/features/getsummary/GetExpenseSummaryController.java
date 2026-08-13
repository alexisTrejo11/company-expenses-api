package io.github.alexisTrejo11.construction.company.modules.expense.features.getsummary;

import io.github.alexisTrejo11.construction.company.modules.budget.shared.domain.ExpenseCategory;
import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/expenses")
@RequiredArgsConstructor
@Tag(name = "Expenses", description = "Construction expense tracking")
public class GetExpenseSummaryController {
  private final GetExpenseSummaryHandler handler;

  @GetMapping("/summary")
  @Operation(summary = "Expense summary", description = "Aggregates executed totals by budget item or category.")
  public ResponseEntity<ResponseWrapper<?>> getSummary(
      @RequestParam(required = false) Long projectId,
      @RequestParam(required = false) Long budgetId,
      @RequestParam(required = false) Long budgetItemId,
      @RequestParam(required = false) ExpenseCategory category) {
    return ResponseEntity.ok(ResponseWrapper.found(
        handler.execute(new GetExpenseSummaryQuery(projectId, budgetId, budgetItemId, category)),
        "ExpenseSummary"
    ));
  }
}
