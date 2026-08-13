package io.github.alexisTrejo11.construction.company.modules.expense.features.create;

import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/expenses")
@RequiredArgsConstructor
@Tag(name = "Expenses", description = "Construction expense tracking")
public class CreateExpenseController {
  private final CreateExpenseHandler handler;

  @PostMapping
  @Operation(summary = "Register expense", description = "Registers a new expense linked to a budget line item.")
  public ResponseEntity<ResponseWrapper<?>> create(@RequestBody CreateExpenseCommand request) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ResponseWrapper.created(handler.execute(request), "Expense"));
  }
}
