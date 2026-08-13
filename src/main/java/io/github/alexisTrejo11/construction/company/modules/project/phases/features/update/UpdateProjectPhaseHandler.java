package io.github.alexisTrejo11.construction.company.modules.project.phases.features.update;

import io.github.alexisTrejo11.construction.company.modules.project.phases.features.create.CreateProjectPhaseCommand;
import io.github.alexisTrejo11.construction.company.modules.project.shared.dto.ProjectPhaseResponse;
import org.springframework.stereotype.Service;

@Service
public class UpdateProjectPhaseHandler {
  public ProjectPhaseResponse execute(Long projectId, Long phaseId, CreateProjectPhaseCommand command) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
