package io.github.alexisTrejo11.construction.company.modules.expense.features.update;

import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/expenses")
@RequiredArgsConstructor
@Tag(name = "Expenses", description = "Construction expense tracking")
public class UpdateExpenseController {
  private final UpdateExpenseHandler handler;

  @PutMapping("/{expenseId}")
  @Operation(summary = "Update expense", description = "Updates description, amount or vendor while the expense is in DRAFT.")
  public ResponseEntity<ResponseWrapper<?>> update(
      @PathVariable Long expenseId,
      @RequestBody UpdateExpenseCommand request) {
    return ResponseEntity.ok(
        ResponseWrapper.success(handler.execute(expenseId, request), "Expense successfully updated")
    );
  }
}
