package io.github.alexisTrejo11.construction.company.modules.expense.features.getsummary;

import io.github.alexisTrejo11.construction.company.modules.budget.shared.domain.ExpenseCategory;

public record GetExpenseSummaryQuery(Long projectId, Long budgetId, Long budgetItemId, ExpenseCategory category) {
}
