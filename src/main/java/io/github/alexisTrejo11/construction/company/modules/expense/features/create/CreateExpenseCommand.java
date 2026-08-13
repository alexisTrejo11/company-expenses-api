package io.github.alexisTrejo11.construction.company.modules.expense.features.create;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateExpenseCommand(
    @NotNull(message = "Budget item ID is required")
    Long budgetItemId,

    @NotBlank(message = "Expense description is required")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    String description,

    @NotNull(message = "Expense amount is required")
    @DecimalMin(value = "0.01", message = "Expense amount must be greater than zero")
    BigDecimal amount,

    @Size(min = 3, max = 3, message = "Currency code must be 3 characters")
    String currency,

    @NotNull(message = "Expense date is required")
    @PastOrPresent(message = "Expense date cannot be in the future")
    LocalDate expenseDate,

    @Size(max = 100, message = "Receipt number cannot exceed 100 characters")
    String receiptNumber,

    @Size(max = 50, message = "Vendor tax ID cannot exceed 50 characters")
    String vendorTaxId,

    @Size(max = 150, message = "Vendor name cannot exceed 150 characters")
    String vendorName
) {
}
