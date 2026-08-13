package io.github.alexisTrejo11.construction.company.modules.project.members.features.updaterole;

import io.github.alexisTrejo11.construction.company.modules.project.shared.domain.ProjectRole;
import jakarta.validation.constraints.NotNull;

public record UpdateProjectMemberRoleCommand(
    @NotNull(message = "Role is required")
    ProjectRole role
) {
}
