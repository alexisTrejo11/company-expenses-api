package io.github.alexisTrejo11.construction.company.modules.project.features.getsummary;

import io.github.alexisTrejo11.construction.company.modules.project.shared.persistence.entity.ProjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface GetProjectSummaryMapper {

  @Mapping(target = "spentBudget", expression = "java(java.math.BigDecimal.ZERO)")
  @Mapping(target = "remainingBudget", source = "totalBudget")
  @Mapping(target = "completionPercentage", expression = "java(0.0)")
  @Mapping(target = "totalPhases", expression = "java(project.getPhases() == null ? 0 : project.getPhases().size())")
  @Mapping(target = "totalMembers", expression = "java(project.getMembers() == null ? 0 : project.getMembers().size())")
  GetProjectSummaryResponse toResponse(ProjectEntity project);
}
