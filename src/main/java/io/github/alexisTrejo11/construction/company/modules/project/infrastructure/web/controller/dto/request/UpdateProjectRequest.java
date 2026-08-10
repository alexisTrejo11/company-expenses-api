package io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateProjectRequest(
    @Size(max = 150, message = "Project name must not exceed 150 characters")
    String name,

    String description,

    @PositiveOrZero(message = "Total budget must be zero or positive")
    BigDecimal totalBudget,

    @Valid
    SiteLocationRequest location,

    LocalDate startDate,

    LocalDate estimatedEndDate,

    LocalDate actualEndDate
) {}