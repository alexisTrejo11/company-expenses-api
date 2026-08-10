package io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.response;

import java.math.BigDecimal;

public record ProjectSummaryResponse(
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
) {}