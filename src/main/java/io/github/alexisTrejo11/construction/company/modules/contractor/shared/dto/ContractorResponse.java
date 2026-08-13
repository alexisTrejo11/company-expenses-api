package io.github.alexisTrejo11.construction.company.modules.contractor.shared.dto;

public record ContractorResponse(
    Long id,
    String name,
    String specialty,
    String taxId,
    String contactEmail,
    String contactPhone,
    String status
) {
}
