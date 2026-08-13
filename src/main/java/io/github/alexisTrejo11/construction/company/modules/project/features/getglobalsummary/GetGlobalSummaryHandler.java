package io.github.alexisTrejo11.construction.company.modules.project.features.getglobalsummary;

import io.github.alexisTrejo11.construction.company.modules.project.shared.dto.ProjectsGlobalSummaryResponse;
import io.github.alexisTrejo11.construction.company.modules.project.shared.persistence.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetGlobalSummaryHandler {
  private final ProjectRepository repository;

  @Transactional(readOnly = true)
  public ProjectsGlobalSummaryResponse execute() {
    return repository.getGlobalSummary();
  }
}
