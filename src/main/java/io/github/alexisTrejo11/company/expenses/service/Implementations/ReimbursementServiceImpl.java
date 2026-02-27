package io.github.alexisTrejo11.company.expenses.service.Implementations;

import io.github.alexisTrejo11.company.expenses.dto.Reimbursement.ReimbursementDTO;
import io.github.alexisTrejo11.company.expenses.dto.Reimbursement.ReimbursementInsertDTO;
import io.github.alexisTrejo11.company.expenses.mapper.ReimbursementMapper;
import io.github.alexisTrejo11.company.expenses.model.Expense;
import io.github.alexisTrejo11.company.expenses.model.Reimbursement;
import io.github.alexisTrejo11.company.expenses.model.User;
import io.github.alexisTrejo11.company.expenses.shared.MessageGenerator;
import io.github.alexisTrejo11.company.expenses.shared.enums.ExpenseStatus;
import io.github.alexisTrejo11.company.expenses.repository.ExpenseRepository;
import io.github.alexisTrejo11.company.expenses.repository.ReimbursementRepository;
import io.github.alexisTrejo11.company.expenses.repository.UserRepository;
import io.github.alexisTrejo11.company.expenses.service.Interfaces.ReimbursementService;
import io.github.alexisTrejo11.company.expenses.shared.Result;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReimbursementServiceImpl implements ReimbursementService {

    private final ReimbursementRepository reimbursementRepository;
    private final ReimbursementMapper reimbursementMapper;
    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;
    private final MessageGenerator message;

    @Override
    public ReimbursementDTO getReimbursementById(Long reimbursementId) {
        Optional<Reimbursement> optionalReimbursement = reimbursementRepository.findById(reimbursementId);

        return optionalReimbursement
                .map(reimbursementMapper::entityToDTO)
                .orElse(null);
    }

    @Override
    public Page<ReimbursementDTO> getReimbursementByUserId(Long userId, Pageable pageable) {
        boolean isUserExisting = userRepository.existsById(userId);
        if (!isUserExisting) {
            throw new EntityNotFoundException("User With Id [" + userId + "] Not Found");
        }

        Page<Reimbursement> reimbursementPage = reimbursementRepository.findByProcessedBy_Id(userId, pageable);

        return reimbursementPage.map(reimbursementMapper::entityToDTO);
    }

    @Override
    public ReimbursementDTO createReimbursement(ReimbursementInsertDTO reimbursementInsertDTO, String email) {
        Reimbursement reimbursement = reimbursementMapper.insertDtoToEntity(reimbursementInsertDTO);
        setReimbursementRelationShips(reimbursementInsertDTO, reimbursement, email);

        reimbursementRepository.saveAndFlush(reimbursement);

        return reimbursementMapper.entityToDTO(reimbursement);
    }

    private void setReimbursementRelationShips(ReimbursementInsertDTO reimbursementInsertDTO,
                                               Reimbursement reimbursement,
                                               String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Long expenseId = reimbursementInsertDTO.getExpenseId();
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new EntityNotFoundException(message.notFoundPlain("Expense", expenseId)));

        reimbursement.setProcessedBy(user);
        reimbursement.setExpense(expense);

    }

    @Override
    public Result<Void> validate(ReimbursementInsertDTO insertDTO) {
        Expense expense = expenseRepository.findById(insertDTO.getExpenseId())
                .orElseThrow(() -> new EntityNotFoundException(message.notFoundPlain("Expense", insertDTO.getExpenseId())));

        if (expense.getStatus() != ExpenseStatus.APPROVED) {
            return Result.error("Only approved expenses can be reimbursement");
        }


        return Result.success();
    }
}
