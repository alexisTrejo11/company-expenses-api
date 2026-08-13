package io.github.alexisTrejo11.construction.company.modules.project.members.features.add;

import io.github.alexisTrejo11.construction.company.modules.project.shared.domain.ProjectRole;
import jakarta.validation.constraints.NotNull;

public record AddProjectMemberCommand(
    @NotNull(message = "User ID is required")
    Long userId,

    @NotNull(message = "Role is required")
    ProjectRole role
) {
}
