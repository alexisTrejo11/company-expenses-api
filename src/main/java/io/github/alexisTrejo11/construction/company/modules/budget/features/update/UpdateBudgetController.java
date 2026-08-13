package io.github.alexisTrejo11.construction.company.modules.budget.features.update;

import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/budgets")
@RequiredArgsConstructor
@Tag(name = "Budget", description = "Core budget operations")
public class UpdateBudgetController {
  private final UpdateBudgetHandler handler;

  @PutMapping("/{budgetId}")
  @Operation(summary = "Update budget", description = "Updates target amount, currency, or budget threshold limits.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Budget updated")
  })
  public ResponseEntity<ResponseWrapper<?>> updateBudget(
      @PathVariable Long budgetId,
      @RequestBody @Valid UpdateBudgetCommand request) {
    return ResponseEntity.ok(
        ResponseWrapper.success(handler.execute(budgetId, request), "Budget successfully updated")
    );
  }
}
