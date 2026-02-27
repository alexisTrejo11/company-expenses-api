package io.github.alexisTrejo11.company.expenses.shared.mapper;

import io.github.alexisTrejo11.company.expenses.shared.dto.reimbursement.ReimbursementDTO;
import io.github.alexisTrejo11.company.expenses.shared.dto.reimbursement.ReimbursementInsertDTO;
import io.github.alexisTrejo11.company.expenses.model.Reimbursement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReimbursementMapper {

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "reimbursementDate", source = "reimbursementDate")
    Reimbursement insertDtoToEntity(ReimbursementInsertDTO expenseInsertDTO);

    @Mapping(target = "processedBy", source = "processedBy.id")
    @Mapping(target = "expense.approvedById", source = "expense.approvedBy.id")
    ReimbursementDTO entityToDTO(Reimbursement reimbursement);

}
