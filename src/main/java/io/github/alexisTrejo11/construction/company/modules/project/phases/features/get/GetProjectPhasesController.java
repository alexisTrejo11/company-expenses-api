package io.github.alexisTrejo11.construction.company.modules.project.phases.features.get;

import io.github.alexisTrejo11.construction.company.modules.project.shared.dto.ProjectPhaseResponse;
import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v2/api/projects/{projectId}/phases")
@RequiredArgsConstructor
public class GetProjectPhasesController {
  private final GetProjectPhasesHandler handler;

  @GetMapping
  public ResponseWrapper<List<ProjectPhaseResponse>> getProjectPhases(@PathVariable Long projectId) {
    return ResponseWrapper.found(handler.execute(new GetProjectPhasesQuery(projectId)), "ProjectPhase");
  }
}
