package io.github.alexisTrejo11.construction.company.modules.budget.shared.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BudgetResponse(
    Long id,
    Long projectId,
    BigDecimal estimatedAmount,
    BigDecimal executedAmount,
    BigDecimal remainingBalance,
    String currency,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
