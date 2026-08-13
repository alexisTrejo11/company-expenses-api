package io.github.alexisTrejo11.construction.company.modules.budget.features.getbyid;

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
public class GetBudgetByIdController {
  private final GetBudgetByIdHandler handler;

  @GetMapping("/{budgetId}")
  @Operation(summary = "Get budget by ID", description = "Fetches budget details by budget ID.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Budget found")
  })
  public ResponseEntity<ResponseWrapper<?>> getBudget(@PathVariable Long budgetId) {
    return ResponseEntity.ok(
        ResponseWrapper.found(handler.execute(new GetBudgetByIdQuery(budgetId)), "Budget", "ID", budgetId)
    );
  }
}
