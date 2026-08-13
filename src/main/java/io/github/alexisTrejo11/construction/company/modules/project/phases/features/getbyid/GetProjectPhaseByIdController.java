package io.github.alexisTrejo11.construction.company.modules.project.phases.features.getbyid;

import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/projects/{projectId}/phases")
@RequiredArgsConstructor
public class GetProjectPhaseByIdController {
  private final GetProjectPhaseByIdHandler handler;

  @GetMapping("/{phaseId}")
  public ResponseWrapper<?> getProjectPhase(@PathVariable Long projectId, @PathVariable Long phaseId) {
    return ResponseWrapper.found(handler.execute(new GetProjectPhaseByIdQuery(projectId, phaseId)), "ProjectPhase");
  }
}
