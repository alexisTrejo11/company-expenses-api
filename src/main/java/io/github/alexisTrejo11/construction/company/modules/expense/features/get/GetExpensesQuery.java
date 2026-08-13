package io.github.alexisTrejo11.construction.company.modules.expense.features.get;

import io.github.alexisTrejo11.construction.company.modules.expense.shared.domain.ExpenseStatus;
import io.github.alexisTrejo11.construction.company.shared.dto.PageRequest;

public record GetExpensesQuery(
    Long projectId,
    Long budgetId,
    String vendorName,
    ExpenseStatus status,
    PageRequest pageRequest
) {
}
