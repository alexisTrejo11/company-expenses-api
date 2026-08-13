package io.github.alexisTrejo11.construction.company.modules.expense.features.submit;

import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/expenses")
@RequiredArgsConstructor
@Tag(name = "Expenses", description = "Construction expense tracking")
public class SubmitExpenseController {
  private final SubmitExpenseHandler handler;

  @PostMapping("/{expenseId}/submit")
  @Operation(summary = "Submit expense", description = "Moves the expense from DRAFT to PENDING_APPROVAL.")
  public ResponseEntity<ResponseWrapper<?>> submit(@PathVariable Long expenseId) {
    return ResponseEntity.ok(
        ResponseWrapper.success(handler.execute(new SubmitExpenseCommand(expenseId)), "Expense successfully submitted")
    );
  }
}
