package io.github.alexisTrejo11.construction.company.modules.budget.expenses.features.get;

import io.github.alexisTrejo11.construction.company.modules.expense.shared.dto.ExpenseResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class GetBudgetExpensesHandler {
  public Page<ExpenseResponse> execute(GetBudgetExpensesQuery query) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
