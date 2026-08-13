package io.github.alexisTrejo11.construction.company.modules.expense.features.get;

import io.github.alexisTrejo11.construction.company.modules.expense.shared.dto.ExpenseResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class GetExpensesHandler {
  public Page<ExpenseResponse> execute(GetExpensesQuery query) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
