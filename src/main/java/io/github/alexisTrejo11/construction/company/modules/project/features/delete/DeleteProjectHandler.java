package io.github.alexisTrejo11.construction.company.modules.project.features.delete;

import io.github.alexisTrejo11.construction.company.modules.project.shared.persistence.ProjectRepository;
import io.github.alexisTrejo11.construction.company.shared.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteProjectHandler {
  private final ProjectRepository repository;

  @Transactional
  public Result<Void> execute(DeleteProjectCommand command) {
    return repository.findById(command.projectId())
        .map(project -> {
          project.delete();
          repository.save(project);
          return Result.<Void>success();
        })
        .orElseGet(() -> Result.notFound("Project not found"));
  }
}
