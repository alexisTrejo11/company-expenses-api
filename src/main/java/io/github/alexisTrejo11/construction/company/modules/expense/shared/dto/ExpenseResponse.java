package io.github.alexisTrejo11.construction.company.modules.expense.shared.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ExpenseResponse(
    Long id,
    Long budgetId,
    Long budgetItemId,
    String description,
    BigDecimal amount,
    String currency,
    LocalDate expenseDate,
    String invoiceUrl,
    String receiptNumber,
    String vendorTaxId,
    String vendorName,
    String status,
    List<ExpenseAttachmentResponse> attachments,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
