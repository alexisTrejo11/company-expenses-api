package io.github.alexisTrejo11.construction.company.modules.budget.features.getsummary;

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
@RequestMapping("/v2/api/budgets")
@RequiredArgsConstructor
@Tag(name = "Budget", description = "Core budget operations")
public class GetBudgetSummaryController {
  private final GetBudgetSummaryHandler handler;

  @GetMapping("/{budgetId}/summary")
  @Operation(summary = "Get budget summary", description = "Returns financial metrics (estimated vs. spent, variance, balance).")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Summary found")
  })
  public ResponseEntity<ResponseWrapper<?>> getBudgetSummary(@PathVariable Long budgetId) {
    return ResponseEntity.ok(
        ResponseWrapper.found(handler.execute(new GetBudgetSummaryQuery(budgetId)), "Budget", "ID", budgetId)
    );
  }
}
