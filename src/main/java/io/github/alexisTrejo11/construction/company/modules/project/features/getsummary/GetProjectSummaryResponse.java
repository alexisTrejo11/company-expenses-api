package io.github.alexisTrejo11.construction.company.modules.project.features.getsummary;

import java.math.BigDecimal;

public record GetProjectSummaryResponse(
    Long id,
    String name,
    String code,
    String status,
    BigDecimal totalBudget,
    BigDecimal spentBudget,
    BigDecimal remainingBudget,
    Double completionPercentage,
    int totalPhases,
    int totalMembers
) {
}
