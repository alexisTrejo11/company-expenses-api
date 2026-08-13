package io.github.alexisTrejo11.construction.company.modules.project.features.create;

import io.github.alexisTrejo11.construction.company.modules.project.shared.mapper.ProjectResponseMapper;
import io.github.alexisTrejo11.construction.company.modules.project.shared.persistence.entity.ProjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    uses = ProjectResponseMapper.class
)
public interface CreateProjectMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "deletedAt", ignore = true)
  @Mapping(target = "version", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "actualEndDate", ignore = true)
  @Mapping(target = "phases", ignore = true)
  @Mapping(target = "members", ignore = true)
  ProjectEntity toEntity(CreateProjectCommand request);
}
