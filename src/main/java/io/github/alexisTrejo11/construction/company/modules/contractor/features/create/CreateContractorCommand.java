package io.github.alexisTrejo11.construction.company.modules.contractor.features.create;

public record CreateContractorCommand(
    String name,
    String specialty,
    String taxId,
    String contactEmail,
    String contactPhone
) {
}
