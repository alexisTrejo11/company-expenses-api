package io.github.alexisTrejo11.construction.company.modules.contractor.features.getprojects;

public record ContractorProjectResponse(
    Long projectId,
    String projectName,
    String projectCode,
    String role,
    String status
) {
}
