package io.github.alexisTrejo11.company.expenses.shared.mapper;

import io.github.alexisTrejo11.company.expenses.model.Expense;
import io.github.alexisTrejo11.company.expenses.model.Reimbursement;
import io.github.alexisTrejo11.company.expenses.model.User;
import io.github.alexisTrejo11.company.expenses.shared.dto.expenses.ExpenseDTO;
import io.github.alexisTrejo11.company.expenses.shared.dto.reimbursement.ReimbursementDTO;
import io.github.alexisTrejo11.company.expenses.shared.dto.reimbursement.ReimbursementInsertDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-27T14:24:54-0600",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.jar, environment: Java 17.0.12 (JetBrains s.r.o.)"
)
@Component
public class ReimbursementMapperImpl implements ReimbursementMapper {

    @Override
    public Reimbursement insertDtoToEntity(ReimbursementInsertDTO expenseInsertDTO) {
        if ( expenseInsertDTO == null ) {
            return null;
        }

        Reimbursement reimbursement = new Reimbursement();

        reimbursement.setReimbursementDate( expenseInsertDTO.getReimbursementDate() );

        reimbursement.setCreatedAt( java.time.LocalDateTime.now() );
        reimbursement.setUpdatedAt( java.time.LocalDateTime.now() );

        return reimbursement;
    }

    @Override
    public ReimbursementDTO entityToDTO(Reimbursement reimbursement) {
        if ( reimbursement == null ) {
            return null;
        }

        ReimbursementDTO reimbursementDTO = new ReimbursementDTO();

        reimbursementDTO.setExpense( expenseToExpenseDTO( reimbursement.getExpense() ) );
        reimbursementDTO.setProcessedBy( reimbursementProcessedById( reimbursement ) );
        reimbursementDTO.setId( reimbursement.getId() );
        reimbursementDTO.setReimbursementDate( reimbursement.getReimbursementDate() );

        return reimbursementDTO;
    }

    private Long expenseApprovedById(Expense expense) {
        if ( expense == null ) {
            return null;
        }
        User approvedBy = expense.getApprovedBy();
        if ( approvedBy == null ) {
            return null;
        }
        Long id = approvedBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected ExpenseDTO expenseToExpenseDTO(Expense expense) {
        if ( expense == null ) {
            return null;
        }

        ExpenseDTO expenseDTO = new ExpenseDTO();

        expenseDTO.setApprovedById( expenseApprovedById( expense ) );
        expenseDTO.setId( expense.getId() );
        expenseDTO.setAmount( expense.getAmount() );
        expenseDTO.setCategory( expense.getCategory() );
        expenseDTO.setDescription( expense.getDescription() );
        expenseDTO.setDate( expense.getDate() );
        expenseDTO.setReceiptUrl( expense.getReceiptUrl() );
        expenseDTO.setStatus( expense.getStatus() );
        expenseDTO.setRejectionReason( expense.getRejectionReason() );

        return expenseDTO;
    }

    private Long reimbursementProcessedById(Reimbursement reimbursement) {
        if ( reimbursement == null ) {
            return null;
        }
        User processedBy = reimbursement.getProcessedBy();
        if ( processedBy == null ) {
            return null;
        }
        Long id = processedBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
