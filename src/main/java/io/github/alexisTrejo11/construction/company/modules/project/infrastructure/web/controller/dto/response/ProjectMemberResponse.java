package io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.response;

import java.time.LocalDateTime;

public record ProjectMemberResponse(
    Long id,
    Long userId,
    String role,
    LocalDateTime assignedAt
) {}