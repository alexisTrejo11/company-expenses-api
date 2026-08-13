package io.github.alexisTrejo11.construction.company.modules.budget.items.features.get;

import io.github.alexisTrejo11.construction.company.modules.budget.shared.domain.ExpenseCategory;
import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import io.github.alexisTrejo11.construction.company.shared.dto.PageRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/budgets/{budgetId}/items")
@RequiredArgsConstructor
@Tag(name = "Budget Items", description = "Cost items allocation (budget line items)")
public class GetBudgetItemsController {
  private final GetBudgetItemsHandler handler;

  @GetMapping
  @Operation(summary = "List budget items", description = "Retrieves a paginated list of budget allocations.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Items found")
  })
  public ResponseEntity<ResponseWrapper<?>> getBudgetItems(
      @PathVariable Long budgetId,
      @ModelAttribute PageRequest pageRequest,
      @RequestParam(required = false) ExpenseCategory category,
      @RequestParam(required = false) Long phaseId) {
    return ResponseEntity.ok(ResponseWrapper.found(
        handler.execute(new GetBudgetItemsQuery(budgetId, category, phaseId, pageRequest)),
        "BudgetItem"
    ));
  }
}
