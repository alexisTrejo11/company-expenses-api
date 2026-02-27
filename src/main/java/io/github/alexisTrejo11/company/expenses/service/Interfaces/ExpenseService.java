package io.github.alexisTrejo11.company.expenses.service.Interfaces;

import io.github.alexisTrejo11.company.expenses.dto.Expenses.ExpenseDTO;
import io.github.alexisTrejo11.company.expenses.dto.Expenses.ExpenseInsertDTO;
import io.github.alexisTrejo11.company.expenses.dto.Expenses.ExpenseRejectDTO;
import io.github.alexisTrejo11.company.expenses.shared.enums.ExpenseStatus;
import io.github.alexisTrejo11.company.expenses.shared.Result;
import io.github.alexisTrejo11.company.expenses.shared.ExpenseSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface ExpenseService {
    ExpenseDTO getExpenseById(Long expenseId);
    Page<ExpenseDTO> getExpenseByUserEmail(String email, Pageable pageable);
    Page<ExpenseDTO> getAllExpenseByStatus(ExpenseStatus expenseStatus, Pageable sortedPage);
    ExpenseSummary getExpenseSummaryByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    Result<Void> validate(ExpenseInsertDTO expenseInsertDTO);
    ExpenseDTO createExpense(ExpenseInsertDTO expenseInsertDTO, String email, ExpenseStatus expenseStatus);
    ExpenseDTO approveExpense(Long expenseId, String managerEmail);
    ExpenseDTO rejectExpense(ExpenseRejectDTO expenseRejectDTO);

    void softDeleteExpenseById(Long expenseId);
}
