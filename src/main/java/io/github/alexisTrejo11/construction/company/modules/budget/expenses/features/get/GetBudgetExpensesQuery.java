package io.github.alexisTrejo11.construction.company.modules.budget.expenses.features.get;

import io.github.alexisTrejo11.construction.company.shared.dto.PageRequest;

import java.time.LocalDate;

public record GetBudgetExpensesQuery(
    Long budgetId,
    Long itemId,
    LocalDate startDate,
    LocalDate endDate,
    PageRequest pageRequest
) {
}
