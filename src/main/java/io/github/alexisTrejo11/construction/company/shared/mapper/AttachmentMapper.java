package io.github.alexisTrejo11.construction.company.shared.mapper;

import io.github.alexisTrejo11.construction.company.shared.dto.attachements.AttachmentDTO;
import io.github.alexisTrejo11.construction.company.modules.expenses.model.ExpenseAttachment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {

    @Mapping(target = "expense", ignore = true)
    AttachmentDTO entityToDTO(ExpenseAttachment expenseAttachment);
}
