package io.github.alexisTrejo11.construction.company.modules.budget.items.features.get;

import io.github.alexisTrejo11.construction.company.modules.budget.shared.dto.BudgetItemResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class GetBudgetItemsHandler {
  public Page<BudgetItemResponse> execute(GetBudgetItemsQuery query) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
