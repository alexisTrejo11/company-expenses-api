package io.github.alexisTrejo11.construction.company.modules.approval.features.getbytarget;

import io.github.alexisTrejo11.construction.company.modules.approval.shared.domain.ApprovalTargetType;

public record GetApprovalsByTargetQuery(ApprovalTargetType targetType, Long targetId) {
}
