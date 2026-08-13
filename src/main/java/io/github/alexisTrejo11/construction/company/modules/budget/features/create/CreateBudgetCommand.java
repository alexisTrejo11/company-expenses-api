package io.github.alexisTrejo11.construction.company.modules.budget.features.create;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateBudgetCommand(
    @NotNull(message = "Estimated amount is required")
    @DecimalMin(value = "0.01", message = "Estimated amount must be greater than zero")
    @Digits(integer = 13, fraction = 2, message = "Amount exceeds max digits precision (13 integer, 2 fraction)")
    BigDecimal estimatedAmount,

    @NotBlank(message = "Currency is required")
    @Size(min = 3, max = 3, message = "Currency code must be exactly 3 letters (e.g., MXN, USD)")
    String currency,

    @Size(max = 500, message = "Notes cannot exceed 500 characters")
    String notes
) {
}
