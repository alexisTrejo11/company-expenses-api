package io.github.alexisTrejo11.construction.company.modules.budget.items.features.create;

import io.github.alexisTrejo11.construction.company.modules.budget.shared.domain.ExpenseCategory;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateBudgetItemCommand(
    Long phaseId,

    @NotBlank(message = "Item description is required")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    String description,

    @NotNull(message = "Category is required")
    ExpenseCategory category,

    @NotBlank(message = "Unit of measure is required")
    @Size(max = 20, message = "Unit code cannot exceed 20 characters")
    String unit,

    @NotNull(message = "Planned quantity is required")
    @DecimalMin(value = "0.0001", message = "Quantity must be greater than zero")
    BigDecimal plannedQuantity,

    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.01", message = "Unit price must be greater than zero")
    BigDecimal unitPrice
) {
}
