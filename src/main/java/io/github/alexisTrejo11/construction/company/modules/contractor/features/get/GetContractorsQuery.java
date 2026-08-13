package io.github.alexisTrejo11.construction.company.modules.contractor.features.get;

import io.github.alexisTrejo11.construction.company.modules.contractor.shared.domain.ContractorStatus;
import io.github.alexisTrejo11.construction.company.shared.dto.PageRequest;

public record GetContractorsQuery(
    String specialty,
    ContractorStatus status,
    PageRequest pageRequest
) {
}
