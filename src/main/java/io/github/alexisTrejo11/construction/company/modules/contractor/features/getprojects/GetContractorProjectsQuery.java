package io.github.alexisTrejo11.construction.company.modules.contractor.features.getprojects;

import io.github.alexisTrejo11.construction.company.shared.dto.PageRequest;

public record GetContractorProjectsQuery(Long contractorId, PageRequest pageRequest) {
}
