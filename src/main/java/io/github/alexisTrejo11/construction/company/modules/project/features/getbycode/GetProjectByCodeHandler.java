package io.github.alexisTrejo11.construction.company.modules.project.features.getbycode;

import io.github.alexisTrejo11.construction.company.modules.project.shared.dto.ProjectResponse;
import io.github.alexisTrejo11.construction.company.modules.project.shared.mapper.ProjectResponseMapper;
import io.github.alexisTrejo11.construction.company.modules.project.shared.persistence.ProjectRepository;
import io.github.alexisTrejo11.construction.company.shared.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetProjectByCodeHandler {
  private final ProjectRepository repository;
  private final ProjectResponseMapper mapper;

  @Transactional(readOnly = true)
  public Result<ProjectResponse> execute(GetProjectByCodeQuery query) {
    return repository.findByCode(query.code())
        .map(mapper::toResponse)
        .map(Result::success)
        .orElseGet(() -> Result.notFound("Project not found"));
  }
}
