package io.github.alexisTrejo11.construction.company.modules.approval.features.getpending;

import io.github.alexisTrejo11.construction.company.shared.dto.PageRequest;

public record GetPendingApprovalsQuery(Long userId, PageRequest pageRequest) {
}
