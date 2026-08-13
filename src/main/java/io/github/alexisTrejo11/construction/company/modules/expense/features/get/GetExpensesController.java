package io.github.alexisTrejo11.construction.company.modules.expense.features.get;

import io.github.alexisTrejo11.construction.company.modules.expense.shared.domain.ExpenseStatus;
import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import io.github.alexisTrejo11.construction.company.shared.dto.PageRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/expenses")
@RequiredArgsConstructor
@Tag(name = "Expenses", description = "Construction expense tracking")
public class GetExpensesController {
  private final GetExpensesHandler handler;

  @GetMapping
  @Operation(summary = "List expenses", description = "Paginated expenses filtered by project, budget, vendor or status.")
  public ResponseEntity<ResponseWrapper<?>> getExpenses(
      @ModelAttribute PageRequest pageRequest,
      @RequestParam(required = false) Long projectId,
      @RequestParam(required = false) Long budgetId,
      @RequestParam(required = false) String vendorName,
      @RequestParam(required = false) ExpenseStatus status) {
    return ResponseEntity.ok(ResponseWrapper.found(
        handler.execute(new GetExpensesQuery(projectId, budgetId, vendorName, status, pageRequest)),
        "Expense"
    ));
  }
}
