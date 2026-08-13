package io.github.alexisTrejo11.construction.company.modules.budget.shared.mapper;

import io.github.alexisTrejo11.construction.company.modules.budget.shared.dto.BudgetItemResponse;
import io.github.alexisTrejo11.construction.company.modules.budget.shared.dto.BudgetResponse;
import io.github.alexisTrejo11.construction.company.modules.budget.shared.persistence.entity.BudgetEntity;
import io.github.alexisTrejo11.construction.company.modules.budget.shared.persistence.entity.BudgetItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface BudgetResponseMapper {

  @Mapping(target = "projectId", source = "project.id")
  @Mapping(target = "remainingBalance", expression = "java(budget.getRemainingBalance().amount())")
  BudgetResponse toResponse(BudgetEntity budget);

  @Mapping(target = "budgetId", source = "budget.id")
  @Mapping(target = "phaseId", source = "phase.id")
  BudgetItemResponse toItemResponse(BudgetItemEntity item);
}
