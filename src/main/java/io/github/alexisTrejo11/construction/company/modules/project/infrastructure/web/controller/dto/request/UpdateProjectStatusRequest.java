package io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.request;

import io.github.alexisTrejo11.construction.company.modules.project.domain.ProjectStatus;

import jakarta.validation.constraints.NotNull;

public record UpdateProjectStatusRequest(
    @NotNull(message = "Status is required")
    ProjectStatus status
) {}