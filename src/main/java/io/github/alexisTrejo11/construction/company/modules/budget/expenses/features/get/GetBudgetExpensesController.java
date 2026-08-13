package io.github.alexisTrejo11.construction.company.modules.budget.expenses.features.get;

import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import io.github.alexisTrejo11.construction.company.shared.dto.PageRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/v2/api/budgets/{budgetId}/expenses")
@RequiredArgsConstructor
@Tag(name = "Budget Expenses", description = "Expense execution and tracking")
public class GetBudgetExpensesController {
  private final GetBudgetExpensesHandler handler;

  @GetMapping
  @Operation(summary = "Search budget expenses", description = "Searches and filters historical expense transactions.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Expenses found")
  })
  public ResponseEntity<ResponseWrapper<?>> getBudgetExpenses(
      @PathVariable Long budgetId,
      @ModelAttribute PageRequest pageRequest,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
      @RequestParam(required = false) Long itemId) {
    return ResponseEntity.ok(ResponseWrapper.found(
        handler.execute(new GetBudgetExpensesQuery(budgetId, itemId, startDate, endDate, pageRequest)),
        "Expense"
    ));
  }
}
