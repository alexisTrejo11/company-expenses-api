package io.github.alexisTrejo11.construction.company.modules.budget.features.update;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdateBudgetCommand(
    @NotNull(message = "Estimated amount is required")
    @DecimalMin(value = "0.01", message = "Estimated amount must be greater than zero")
    BigDecimal estimatedAmount,

    @NotBlank(message = "Currency code is required")
    @Size(min = 3, max = 3, message = "Currency code must be 3 characters")
    String currency
) {
}
