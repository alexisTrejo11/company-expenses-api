package io.github.alexisTrejo11.company.expenses.mapper;

import io.github.alexisTrejo11.company.expenses.dto.Attachements.AttachmentDTO;
import io.github.alexisTrejo11.company.expenses.model.ExpenseAttachment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {

    @Mapping(target = "expense", ignore = true)
    AttachmentDTO entityToDTO(ExpenseAttachment expenseAttachment);
}
