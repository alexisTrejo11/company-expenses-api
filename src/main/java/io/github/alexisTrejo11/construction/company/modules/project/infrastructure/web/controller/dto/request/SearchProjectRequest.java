package io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.request;

import io.github.alexisTrejo11.construction.company.modules.project.domain.ProjectStatus;
import io.github.alexisTrejo11.construction.company.shared.dto.PageRequest;
import jakarta.validation.constraints.Size;

public record SearchProjectRequest(
    @Size(min = 3, max = 255)
    String search,
    ProjectStatus status,
    @Size(min = 3, max = 255)
    String city,
    PageRequest pageRequest) {
}
