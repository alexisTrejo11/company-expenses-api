package io.github.alexisTrejo11.construction.company.modules.budget.expenses.features.create;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LogExpenseCommand(
    @NotNull(message = "Budget item ID is required to associate this expense")
    Long budgetItemId,

    @NotBlank(message = "Expense description is required")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    String description,

    @NotNull(message = "Expense amount is required")
    @DecimalMin(value = "0.01", message = "Expense amount must be greater than zero")
    BigDecimal amount,

    @NotNull(message = "Expense date is required")
    @PastOrPresent(message = "Expense date cannot be in the future")
    LocalDate expenseDate,

    @Size(max = 100, message = "Receipt/Invoice number cannot exceed 100 characters")
    String receiptNumber,

    @Size(max = 150, message = "Vendor name cannot exceed 150 characters")
    String vendorName
) {
}
