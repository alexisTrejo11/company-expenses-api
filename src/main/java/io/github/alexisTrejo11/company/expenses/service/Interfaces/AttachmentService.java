package io.github.alexisTrejo11.company.expenses.service.Interfaces;

import io.github.alexisTrejo11.company.expenses.dto.Attachements.AttachmentDTO;

import java.util.List;

public interface AttachmentService {

    List<AttachmentDTO> getAttachmentsByExpenseId(Long expenseId);
    void createAttachment(Long expenseId, String fileURL);
}
