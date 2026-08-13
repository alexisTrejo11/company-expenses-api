package io.github.alexisTrejo11.construction.company.modules.expense.shared.dto;

import java.time.LocalDateTime;

public record ExpenseAttachmentResponse(
    Long id,
    String fileName,
    String contentType,
    String url,
    LocalDateTime createdAt
) {
}
