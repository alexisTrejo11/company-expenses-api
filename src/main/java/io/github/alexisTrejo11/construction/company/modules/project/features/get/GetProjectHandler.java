package io.github.alexisTrejo11.construction.company.modules.project.features.get;

import io.github.alexisTrejo11.construction.company.modules.project.shared.dto.ProjectResponse;
import io.github.alexisTrejo11.construction.company.modules.project.shared.mapper.ProjectResponseMapper;
import io.github.alexisTrejo11.construction.company.modules.project.shared.persistence.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetProjectHandler {
  private final ProjectRepository repository;
  private final ProjectResponseMapper mapper;

  @Transactional(readOnly = true)
  public Page<ProjectResponse> execute(GetProjectQuery query) {
    return repository.findWithFilters(
        query.search(),
        query.status(),
        query.city(),
        query.pageRequest().toPageable()
    ).map(mapper::toResponse);
  }
}
