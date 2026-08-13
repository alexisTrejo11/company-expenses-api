package io.github.alexisTrejo11.construction.company.modules.project.shared.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProjectPhaseResponse(
    Long id,
    String name,
    Integer sequenceOrder,
    BigDecimal allocatedBudget,
    LocalDate startDate,
    LocalDate endDate
) {
}
