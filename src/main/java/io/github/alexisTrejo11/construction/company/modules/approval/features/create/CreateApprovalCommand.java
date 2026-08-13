package io.github.alexisTrejo11.construction.company.modules.approval.features.create;

import io.github.alexisTrejo11.construction.company.modules.approval.shared.domain.ApprovalTargetType;

public record CreateApprovalCommand(
    ApprovalTargetType targetType,
    Long targetId,
    String comment
) {
}
