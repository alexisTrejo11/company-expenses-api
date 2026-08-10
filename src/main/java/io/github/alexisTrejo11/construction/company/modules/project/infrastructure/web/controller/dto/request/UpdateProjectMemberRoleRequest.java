package io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.request;

import io.github.alexisTrejo11.construction.company.modules.project.domain.ProjectRole;
import jakarta.validation.constraints.NotNull;

public record UpdateProjectMemberRoleRequest(
    @NotNull(message = "Role is required")
    ProjectRole role
) {}