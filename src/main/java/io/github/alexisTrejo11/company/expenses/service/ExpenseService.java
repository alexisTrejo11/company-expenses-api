package io.github.alexisTrejo11.company.expenses.service;

import io.github.alexisTrejo11.company.expenses.shared.dto.expenses.ExpenseDTO;
import io.github.alexisTrejo11.company.expenses.shared.dto.expenses.ExpenseInsertDTO;
import io.github.alexisTrejo11.company.expenses.shared.dto.expenses.ExpenseRejectDTO;
import io.github.alexisTrejo11.company.expenses.shared.mapper.ExpenseMapper;
import io.github.alexisTrejo11.company.expenses.model.Expense;
import io.github.alexisTrejo11.company.expenses.model.User;
import io.github.alexisTrejo11.company.expenses.repository.UserRepository;
import io.github.alexisTrejo11.company.expenses.shared.MessageGenerator;
import io.github.alexisTrejo11.company.expenses.shared.enums.ExpenseStatus;
import io.github.alexisTrejo11.company.expenses.repository.ExpenseRepository;
import io.github.alexisTrejo11.company.expenses.shared.Result;
import io.github.alexisTrejo11.company.expenses.shared.dto.expenses.ExpenseSummary;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final MessageGenerator message;
    private final ExpenseMapper expenseMapper;
    private final UserRepository userRepository;
    private final ExpenseSummaryService expenseSummaryService;
    public ExpenseDTO getExpenseById(Long expenseId) {
        Optional<Expense> optionalExpense = expenseRepository.findById(expenseId);
        return optionalExpense
                .map(expenseMapper::entityToDTO)
                .orElse(null);
    }
    public Page<ExpenseDTO> getExpenseByUserEmail(String email, Pageable pageable) {
        Page<Expense> expenses = expenseRepository.findByUserEmail(email, pageable);
        return expenses.map(expenseMapper::entityToDTO);
    }

    @Cacheable(value = "expensesByStatusCache", key = "#expenseStatus")
    public Page<ExpenseDTO> getAllExpenseByStatus(ExpenseStatus expenseStatus, Pageable sortedPageable) {
        Page<Expense> expenses = expenseRepository.findByStatus(expenseStatus, sortedPageable);

        return expenses.map(expenseMapper::entityToDTO);
    }
    @Cacheable(value = "expenseSummaryCache", key = "'summary_' + #startDate + '_' + #endDate")
    public ExpenseSummary getExpenseSummaryByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
            CompletableFuture<ExpenseSummary> expenseSummaryFuture = expenseSummaryService.generateExpenseSummary(startDate, endDate);
            return expenseSummaryFuture.join();
    }
    public Result<Void> validate(ExpenseInsertDTO expenseInsertDTO) {
        double maxAmount = 100000.0;
        if (expenseInsertDTO.getAmount() != null && expenseInsertDTO.getAmount() > maxAmount) {
            return Result.error("Amount exceeds the maximum limit of " + maxAmount);
        }

        LocalDate maxDate = LocalDate.now().plusMonths(10);
        if (expenseInsertDTO.getDate() != null && expenseInsertDTO.getDate().isAfter(maxDate)) {
            return Result.error("Date cannot be in the future. Maximum date is " + maxDate);
        }

        return Result.success();
    }
    @Transactional
    public ExpenseDTO createExpense(ExpenseInsertDTO expenseInsertDTO, String email, ExpenseStatus expenseStatus) {
        Expense expense = expenseMapper.insertDtoToEntity(expenseInsertDTO);
        expense.setStatus(expenseStatus);
        expense.setUser(getUser(email));

        expenseRepository.saveAndFlush(expense);
        return expenseMapper.entityToDTO(expense);

    }
    @Transactional
    public ExpenseDTO approveExpense(Long id, String managerEmail) {
       Expense expense = expenseRepository.findById(id).orElseThrow(
               () -> new EntityNotFoundException(message.notFoundPlain("Expense", "id", id)));

       validateApproveOrReject(expense);

        expense.setApprovedBy(getUser(managerEmail));
        expense.setStatus(ExpenseStatus.APPROVED);
        expenseRepository.saveAndFlush(expense);

        return expenseMapper.entityToDTO(expense);
    }

    @Transactional
    public ExpenseDTO rejectExpense(ExpenseRejectDTO expenseRejectDTO) {
        Long id = expenseRejectDTO.getExpenseId();
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(message.notFoundPlain("Expense", id)));

        validateApproveOrReject(expense);

        expense.setAsRejected(expenseRejectDTO.getRejectReason());
        expenseRepository.saveAndFlush(expense);

        return expenseMapper.entityToDTO(expense);
    }

    @Transactional
    public void softDeleteExpenseById(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new EntityNotFoundException(message.notFoundPlain("Expense", expenseId)));

        expense.setAsDeleted();
        expenseRepository.save(expense);

    }

    public void validateApproveOrReject(Expense expense) {
        if (!isExpensePending(expense)) {
            throw new IllegalArgumentException("Only Pending expenses can be Approved or Rejected");
        }
    }

    private boolean isExpensePending(Expense expense) {
        return expense.getStatus() == ExpenseStatus.PENDING;
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(message.notFoundPlain("Email", "email", email)));
    }
}

