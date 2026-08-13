package io.github.alexisTrejo11.construction.company.modules.budget.items.features.delete;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/budgets/{budgetId}/items")
@RequiredArgsConstructor
@Tag(name = "Budget Items", description = "Cost items allocation (budget line items)")
public class DeleteBudgetItemController {
  private final DeleteBudgetItemHandler handler;

  @DeleteMapping("/{itemId}")
  @Operation(summary = "Delete budget item", description = "Removes an unexecuted line item allocation.")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Item deleted")
  })
  public ResponseEntity<Void> deleteBudgetItem(
      @PathVariable Long budgetId,
      @PathVariable Long itemId) {
    handler.execute(new DeleteBudgetItemCommand(budgetId, itemId));
    return ResponseEntity.noContent().build();
  }
}
