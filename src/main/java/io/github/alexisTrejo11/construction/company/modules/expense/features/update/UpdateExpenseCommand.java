package io.github.alexisTrejo11.construction.company.modules.expense.features.update;

import java.math.BigDecimal;

public record UpdateExpenseCommand(
    String description,
    BigDecimal amount,
    String vendorName,
    String vendorTaxId,
    String receiptNumber
) {
}
