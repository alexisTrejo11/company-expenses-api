package io.github.alexisTrejo11.construction.company.modules.budget.items.features.create;

import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/budgets/{budgetId}/items")
@RequiredArgsConstructor
@Tag(name = "Budget Items", description = "Cost items allocation (budget line items)")
public class CreateBudgetItemController {
  private final CreateBudgetItemHandler handler;

  @PostMapping
  @Operation(summary = "Add budget item", description = "Adds a planned line item (e.g., Concrete Purchase, Machinery Rental).")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Item created")
  })
  public ResponseEntity<ResponseWrapper<?>> createBudgetItem(
      @PathVariable Long budgetId,
      @RequestBody @Valid CreateBudgetItemCommand request) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ResponseWrapper.created(handler.execute(budgetId, request), "BudgetItem"));
  }
}
