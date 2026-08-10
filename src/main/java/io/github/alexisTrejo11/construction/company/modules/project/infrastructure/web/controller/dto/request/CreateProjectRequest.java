package io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateProjectRequest(
    @NotBlank(message = "Project name is required")
    @Size(max = 150, message = "Project name must not exceed 150 characters")
    String name,

    @NotBlank(message = "Project code is required")
    @Size(max = 30, message = "Project code must not exceed 30 characters")
    @Pattern(regexp = "^[A-Z0-9_-]+$", message = "Project code must be alphanumeric and upper case (dashes/underscores allowed)")
    String code,

    String description,

    @NotNull(message = "Total budget is required")
    @PositiveOrZero(message = "Total budget must be zero or positive")
    BigDecimal totalBudget,

    @Valid
    SiteLocationRequest location,

    LocalDate startDate,

    LocalDate estimatedEndDate
) {}