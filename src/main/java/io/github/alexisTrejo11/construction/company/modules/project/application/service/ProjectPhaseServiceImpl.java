package io.github.alexisTrejo11.construction.company.modules.project.application.service;

import io.github.alexisTrejo11.construction.company.modules.project.application.port.input.ProjectPhaseService;
import io.github.alexisTrejo11.construction.company.modules.project.domain.model.ProjectPhase;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.request.CreateProjectPhaseRequest;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.request.ReorderPhasesRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectPhaseServiceImpl implements ProjectPhaseService {

  @Override
  public List<ProjectPhase> getProjectPhases(Long projectId) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public ProjectPhase getProjectPhaseById(Long projectId, Long phaseId) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public ProjectPhase addPhaseToProject(Long projectId, CreateProjectPhaseRequest request) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public ProjectPhase updateProjectPhase(Long projectId, Long phaseId, CreateProjectPhaseRequest request) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void reorderProjectPhases(Long projectId, ReorderPhasesRequest request) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void removePhaseFromProject(Long projectId, Long phaseId) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
