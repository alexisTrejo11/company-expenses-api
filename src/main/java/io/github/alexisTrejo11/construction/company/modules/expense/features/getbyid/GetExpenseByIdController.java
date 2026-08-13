package io.github.alexisTrejo11.construction.company.modules.expense.features.getbyid;

import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/expenses")
@RequiredArgsConstructor
@Tag(name = "Expenses", description = "Construction expense tracking")
public class GetExpenseByIdController {
  private final GetExpenseByIdHandler handler;

  @GetMapping("/{expenseId}")
  @Operation(summary = "Get expense", description = "Returns expense details including attachments and billing data.")
  public ResponseEntity<ResponseWrapper<?>> getExpense(@PathVariable Long expenseId) {
    return ResponseEntity.ok(ResponseWrapper.found(
        handler.execute(new GetExpenseByIdQuery(expenseId)),
        "Expense",
        "ID",
        expenseId
    ));
  }
}
