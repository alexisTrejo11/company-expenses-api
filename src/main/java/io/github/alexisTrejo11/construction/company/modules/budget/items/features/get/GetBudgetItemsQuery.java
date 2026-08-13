package io.github.alexisTrejo11.construction.company.modules.budget.items.features.get;

import io.github.alexisTrejo11.construction.company.modules.budget.shared.domain.ExpenseCategory;
import io.github.alexisTrejo11.construction.company.shared.dto.PageRequest;

public record GetBudgetItemsQuery(
    Long budgetId,
    ExpenseCategory category,
    Long phaseId,
    PageRequest pageRequest
) {
}
