package io.github.alexisTrejo11.construction.company.modules.expense.features.delete;

import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/expenses")
@RequiredArgsConstructor
@Tag(name = "Expenses", description = "Construction expense tracking")
public class DeleteExpenseController {
  private final DeleteExpenseHandler handler;

  @DeleteMapping("/{expenseId}")
  @Operation(summary = "Delete expense", description = "Cancels or removes an expense before it is approved or executed.")
  public ResponseEntity<ResponseWrapper<?>> delete(@PathVariable Long expenseId) {
    handler.execute(new DeleteExpenseCommand(expenseId));
    return ResponseEntity.ok(ResponseWrapper.deleted("Expense"));
  }
}
