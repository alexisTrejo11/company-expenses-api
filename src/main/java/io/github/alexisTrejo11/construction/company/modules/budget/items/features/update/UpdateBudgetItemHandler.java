package io.github.alexisTrejo11.construction.company.modules.budget.items.features.update;

import io.github.alexisTrejo11.construction.company.modules.budget.items.features.create.CreateBudgetItemCommand;
import io.github.alexisTrejo11.construction.company.modules.budget.shared.dto.BudgetItemResponse;
import org.springframework.stereotype.Service;

@Service
public class UpdateBudgetItemHandler {
  public BudgetItemResponse execute(Long budgetId, Long itemId, CreateBudgetItemCommand command) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
