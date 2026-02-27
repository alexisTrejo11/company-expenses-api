package io.github.alexisTrejo11.company.expenses.shared.mapper;

import io.github.alexisTrejo11.company.expenses.model.Expense;
import io.github.alexisTrejo11.company.expenses.model.ExpenseAttachment;
import io.github.alexisTrejo11.company.expenses.model.User;
import io.github.alexisTrejo11.company.expenses.shared.dto.attachements.AttachmentDTO;
import io.github.alexisTrejo11.company.expenses.shared.dto.expenses.ExpenseDTO;
import io.github.alexisTrejo11.company.expenses.shared.dto.expenses.ExpenseInsertDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-27T14:24:35-0600",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class ExpenseMapperImpl implements ExpenseMapper {

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Override
    public Expense insertDtoToEntity(ExpenseInsertDTO expenseInsertDTO) {
        if ( expenseInsertDTO == null ) {
            return null;
        }

        Expense expense = new Expense();

        expense.setAmount( expenseInsertDTO.getAmount() );
        expense.setCategory( expenseInsertDTO.getCategory() );
        expense.setDate( expenseInsertDTO.getDate() );
        expense.setDescription( expenseInsertDTO.getDescription() );
        expense.setReceiptUrl( expenseInsertDTO.getReceiptUrl() );
        expense.setRejectionReason( expenseInsertDTO.getRejectionReason() );

        expense.setCreatedAt( java.time.LocalDateTime.now() );
        expense.setUpdatedAt( java.time.LocalDateTime.now() );

        return expense;
    }

    @Override
    public ExpenseDTO entityToDTO(Expense expense) {
        if ( expense == null ) {
            return null;
        }

        ExpenseDTO expenseDTO = new ExpenseDTO();

        expenseDTO.setApprovedById( expenseApprovedById( expense ) );
        expenseDTO.setUserId( expenseUserId( expense ) );
        expenseDTO.setAttachments( expenseAttachmentListToAttachmentDTOList( expense.getExpenseAttachments() ) );
        expenseDTO.setAmount( expense.getAmount() );
        expenseDTO.setCategory( expense.getCategory() );
        expenseDTO.setDate( expense.getDate() );
        expenseDTO.setDescription( expense.getDescription() );
        expenseDTO.setId( expense.getId() );
        expenseDTO.setReceiptUrl( expense.getReceiptUrl() );
        expenseDTO.setRejectionReason( expense.getRejectionReason() );
        expenseDTO.setStatus( expense.getStatus() );

        return expenseDTO;
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

    private Long expenseUserId(Expense expense) {
        if ( expense == null ) {
            return null;
        }
        User user = expense.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected List<AttachmentDTO> expenseAttachmentListToAttachmentDTOList(List<ExpenseAttachment> list) {
        if ( list == null ) {
            return null;
        }

        List<AttachmentDTO> list1 = new ArrayList<AttachmentDTO>( list.size() );
        for ( ExpenseAttachment expenseAttachment : list ) {
            list1.add( attachmentMapper.entityToDTO( expenseAttachment ) );
        }

        return list1;
    }
}
