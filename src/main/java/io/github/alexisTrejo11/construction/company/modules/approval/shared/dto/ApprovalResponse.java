package io.github.alexisTrejo11.construction.company.modules.approval.shared.dto;

import java.time.LocalDateTime;

public record ApprovalResponse(
    Long id,
    String targetType,
    Long targetId,
    String status,
    String comment,
    Long requestedBy,
    Long reviewedBy,
    LocalDateTime createdAt,
    LocalDateTime reviewedAt
) {
}
