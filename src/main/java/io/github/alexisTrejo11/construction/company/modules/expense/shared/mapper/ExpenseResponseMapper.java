package io.github.alexisTrejo11.construction.company.modules.expense.shared.mapper;

import io.github.alexisTrejo11.construction.company.modules.expense.shared.dto.ExpenseAttachmentResponse;
import io.github.alexisTrejo11.construction.company.modules.expense.shared.dto.ExpenseResponse;
import io.github.alexisTrejo11.construction.company.modules.expense.shared.persistence.entity.ExpenseAttachmentEntity;
import io.github.alexisTrejo11.construction.company.modules.expense.shared.persistence.entity.ExpenseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ExpenseResponseMapper {

  @Mapping(target = "budgetId", source = "budget.id")
  @Mapping(target = "budgetItemId", source = "budgetItem.id")
  ExpenseResponse toResponse(ExpenseEntity expense);

  ExpenseAttachmentResponse toAttachmentResponse(ExpenseAttachmentEntity attachment);
}
