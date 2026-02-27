package io.github.alexisTrejo11.company.expenses.shared.mapper;

import io.github.alexisTrejo11.company.expenses.shared.dto.expenses.ExpenseDTO;
import io.github.alexisTrejo11.company.expenses.shared.dto.expenses.ExpenseInsertDTO;
import io.github.alexisTrejo11.company.expenses.model.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AttachmentMapper.class})
public interface ExpenseMapper {

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    Expense insertDtoToEntity(ExpenseInsertDTO expenseInsertDTO);

    @Mapping(target = "approvedById", source = "approvedBy.id")
    @Mapping(target = "userId", source = "expense.user.id")
    @Mapping(target = "attachments", source = "expense.expenseAttachments")
    ExpenseDTO entityToDTO(Expense expense);
}
