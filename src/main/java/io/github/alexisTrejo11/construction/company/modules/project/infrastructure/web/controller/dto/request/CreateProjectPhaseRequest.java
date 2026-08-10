package io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateProjectPhaseRequest(
    @NotBlank(message = "Phase name is required")
    @Size(max = 100, message = "Phase name must not exceed 100 characters")
    String name,

    @NotNull(message = "Sequence order is required")
    @Min(value = 1, message = "Sequence order must be at least 1")
    Integer sequenceOrder,

    @NotNull(message = "Allocated budget is required")
    @PositiveOrZero(message = "Allocated budget must be zero or positive")
    BigDecimal allocatedBudget,

    LocalDate startDate,

    LocalDate endDate
) {}