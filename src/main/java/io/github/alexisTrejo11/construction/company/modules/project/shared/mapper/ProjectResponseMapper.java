package io.github.alexisTrejo11.construction.company.modules.project.shared.mapper;

import io.github.alexisTrejo11.construction.company.modules.project.shared.domain.SiteLocation;
import io.github.alexisTrejo11.construction.company.modules.project.shared.dto.ProjectMemberResponse;
import io.github.alexisTrejo11.construction.company.modules.project.shared.dto.ProjectPhaseResponse;
import io.github.alexisTrejo11.construction.company.modules.project.shared.dto.ProjectResponse;
import io.github.alexisTrejo11.construction.company.modules.project.shared.dto.SiteLocationRequest;
import io.github.alexisTrejo11.construction.company.modules.project.shared.dto.SiteLocationResponse;
import io.github.alexisTrejo11.construction.company.modules.project.shared.persistence.entity.ProjectEntity;
import io.github.alexisTrejo11.construction.company.modules.project.shared.persistence.entity.ProjectMember;
import io.github.alexisTrejo11.construction.company.modules.project.shared.persistence.entity.ProjectPhase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProjectResponseMapper {

  ProjectResponse toResponse(ProjectEntity project);

  @Mapping(target = "userId", source = "user.id")
  ProjectMemberResponse toMemberResponse(ProjectMember member);

  ProjectPhaseResponse toPhaseResponse(ProjectPhase phase);

  SiteLocationResponse toSiteLocationResponse(SiteLocation siteLocation);

  SiteLocation toSiteLocation(SiteLocationRequest siteLocationRequest);
}
