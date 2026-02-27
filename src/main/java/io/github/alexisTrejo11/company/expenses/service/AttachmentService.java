package io.github.alexisTrejo11.company.expenses.service;

import io.github.alexisTrejo11.company.expenses.shared.dto.attachements.AttachmentDTO;
import io.github.alexisTrejo11.company.expenses.shared.mapper.AttachmentMapper;
import io.github.alexisTrejo11.company.expenses.model.Expense;
import io.github.alexisTrejo11.company.expenses.model.ExpenseAttachment;
import io.github.alexisTrejo11.company.expenses.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final ExpenseRepository expenseRepository;
    private final AttachmentMapper attachmentMapper;

    public List<AttachmentDTO> getAttachmentsByExpenseId(Long expenseId) {
            Optional<Expense> optionalExpense = expenseRepository.findById(expenseId);
             return optionalExpense.map(expense -> expense.getExpenseAttachments()
                     .stream()
                     .map(attachmentMapper::entityToDTO)
                     .toList())
                     .orElse(null);
    }

    public void createAttachment(Long expenseId, String fileURL) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense Not Found"));

        ExpenseAttachment expenseAttachment = new ExpenseAttachment(expense, fileURL);

        expense.addAttachment(expenseAttachment);

        expenseRepository.saveAndFlush(expense);
    }
}
