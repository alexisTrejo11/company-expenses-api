package io.github.alexisTrejo11.construction.company.modules.budget.shared.dto;

import io.github.alexisTrejo11.construction.company.modules.budget.shared.domain.ExpenseCategory;

import java.math.BigDecimal;

public record BudgetItemResponse(
    Long id,
    Long budgetId,
    Long phaseId,
    String description,
    ExpenseCategory category,
    String unit,
    BigDecimal plannedQuantity,
    BigDecimal unitPrice,
    BigDecimal plannedTotal,
    BigDecimal executedTotal
) {
}
