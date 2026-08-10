package io.github.alexisTrejo11.construction.company.modules.project.application.port.input;

import io.github.alexisTrejo11.construction.company.modules.project.domain.model.ProjectPhase;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.request.CreateProjectPhaseRequest;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.request.ReorderPhasesRequest;

import java.util.List;

public interface ProjectPhaseService {
  List<ProjectPhase> getProjectPhases(Long projectId);

  ProjectPhase getProjectPhaseById(Long projectId, Long phaseId);

  ProjectPhase addPhaseToProject(Long projectId, CreateProjectPhaseRequest request);

  ProjectPhase updateProjectPhase(Long projectId, Long phaseId, CreateProjectPhaseRequest request);

  void reorderProjectPhases(Long projectId, ReorderPhasesRequest request);

  void removePhaseFromProject(Long projectId, Long phaseId);
}
