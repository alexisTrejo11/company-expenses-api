package io.github.alexisTrejo11.construction.company.modules.expense.features.getsummary;

import java.math.BigDecimal;

public record GetExpenseSummaryResponse(
    Long budgetItemId,
    String category,
    BigDecimal executedTotal,
    long expenseCount
) {
}
