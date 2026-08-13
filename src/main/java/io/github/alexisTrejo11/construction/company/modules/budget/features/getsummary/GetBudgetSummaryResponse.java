package io.github.alexisTrejo11.construction.company.modules.budget.features.getsummary;

import java.math.BigDecimal;

public record GetBudgetSummaryResponse(
    Long budgetId,
    Long projectId,
    BigDecimal estimatedTotal,
    BigDecimal executedTotal,
    BigDecimal remainingBalance,
    double executionPercentage,
    boolean isOverBudget,
    String currency
) {
}
