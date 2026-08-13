package io.github.alexisTrejo11.construction.company.modules.project.features.getsummary;

import io.github.alexisTrejo11.construction.company.modules.project.shared.persistence.ProjectRepository;
import io.github.alexisTrejo11.construction.company.shared.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetProjectSummaryHandler {
  private final ProjectRepository repository;
  private final GetProjectSummaryMapper mapper;

  @Transactional(readOnly = true)
  public Result<GetProjectSummaryResponse> execute(GetProjectSummaryQuery query) {
    return repository.findById(query.projectId())
        .map(mapper::toResponse)
        .map(Result::success)
        .orElseGet(() -> Result.notFound("Project not found"));
  }
}
