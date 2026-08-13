package io.github.alexisTrejo11.construction.company.modules.budget.items.features.update;

import io.github.alexisTrejo11.construction.company.modules.budget.items.features.create.CreateBudgetItemCommand;
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
@RequestMapping("/v2/api/budgets/{budgetId}/items")
@RequiredArgsConstructor
@Tag(name = "Budget Items", description = "Cost items allocation (budget line items)")
public class UpdateBudgetItemController {
  private final UpdateBudgetItemHandler handler;

  @PutMapping("/{itemId}")
  @Operation(summary = "Update budget item", description = "Modifies planned quantity, unit price, or description.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Item updated")
  })
  public ResponseEntity<ResponseWrapper<?>> updateBudgetItem(
      @PathVariable Long budgetId,
      @PathVariable Long itemId,
      @RequestBody @Valid CreateBudgetItemCommand request) {
    return ResponseEntity.ok(
        ResponseWrapper.success(handler.execute(budgetId, itemId, request), "Budget item successfully updated")
    );
  }
}
