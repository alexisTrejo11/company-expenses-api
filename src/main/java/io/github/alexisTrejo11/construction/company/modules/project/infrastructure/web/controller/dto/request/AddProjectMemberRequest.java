package io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.request;

import io.github.alexisTrejo11.construction.company.modules.project.domain.ProjectRole;

import javax.validation.constraints.NotNull;

public record AddProjectMemberRequest(
    @NotNull(message = "User ID is required")
    Long userId,

    @NotNull(message = "Role is required")
    ProjectRole role
) {}