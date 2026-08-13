package io.github.alexisTrejo11.construction.company.modules.contractor.features.update;

public record UpdateContractorCommand(
    String name,
    String specialty,
    String taxId,
    String contactEmail,
    String contactPhone
) {
}
