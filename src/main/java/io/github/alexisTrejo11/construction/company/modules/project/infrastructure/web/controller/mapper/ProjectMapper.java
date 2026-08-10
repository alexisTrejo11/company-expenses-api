package io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.mapper;

import io.github.alexisTrejo11.construction.company.modules.project.domain.SiteLocation;
import io.github.alexisTrejo11.construction.company.modules.project.domain.model.ProjectEntity;
import io.github.alexisTrejo11.construction.company.modules.project.domain.model.ProjectMember;
import io.github.alexisTrejo11.construction.company.modules.project.domain.model.ProjectPhase;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.request.CreateProjectRequest;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.request.SiteLocationRequest;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.response.ProjectMemberResponse;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.response.ProjectPhaseResponse;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.response.ProjectResponse;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.response.ProjectSummaryResponse;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.response.SiteLocationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProjectMapper {

  ProjectResponse toResponse(ProjectEntity project);

  @Mapping(target = "spentBudget", expression = "java(java.math.BigDecimal.ZERO)")
  @Mapping(target = "remainingBudget", source = "totalBudget")
  @Mapping(target = "completionPercentage", expression = "java(0.0)")
  @Mapping(target = "totalPhases", expression = "java(project.getPhases() == null ? 0 : project.getPhases().size())")
  @Mapping(target = "totalMembers", expression = "java(project.getMembers() == null ? 0 : project.getMembers().size())")
  ProjectSummaryResponse toSummaryResponse(ProjectEntity project);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "deletedAt", ignore = true)
  @Mapping(target = "version", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "actualEndDate", ignore = true)
  @Mapping(target = "phases", ignore = true)
  @Mapping(target = "members", ignore = true)
  ProjectEntity toEntity(CreateProjectRequest request);

  @Mapping(target = "userId", source = "user.id")
  ProjectMemberResponse toMemberResponse(ProjectMember member);

  ProjectPhaseResponse toPhaseResponse(ProjectPhase phase);

  SiteLocationRequest toSiteLocationRequest(SiteLocation siteLocation);

  SiteLocation toSiteLocation(SiteLocationRequest siteLocationRequest);

  SiteLocationResponse toSiteLocationResponse(SiteLocation siteLocation);
}
