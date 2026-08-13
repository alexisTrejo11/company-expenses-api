package io.github.alexisTrejo11.construction.company.modules.project.features.updatestatus;

import io.github.alexisTrejo11.construction.company.modules.project.shared.dto.ProjectResponse;
import io.github.alexisTrejo11.construction.company.modules.project.shared.mapper.ProjectResponseMapper;
import io.github.alexisTrejo11.construction.company.modules.project.shared.persistence.ProjectRepository;
import io.github.alexisTrejo11.construction.company.shared.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateProjectStatusHandler {
  private final ProjectRepository repository;
  private final ProjectResponseMapper mapper;

  @Transactional
  public Result<ProjectResponse> execute(Long projectId, UpdateProjectStatusCommand command) {
    return repository.findById(projectId)
        .map(project -> {
          project.updateStatus(command.status());
          return Result.success(mapper.toResponse(repository.save(project)));
        })
        .orElseGet(() -> Result.notFound("Project not found"));
  }
}
