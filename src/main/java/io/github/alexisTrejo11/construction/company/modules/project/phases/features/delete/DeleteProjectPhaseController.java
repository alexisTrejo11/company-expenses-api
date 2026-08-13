package io.github.alexisTrejo11.construction.company.modules.project.phases.features.delete;

import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/projects/{projectId}/phases")
@RequiredArgsConstructor
public class DeleteProjectPhaseController {
  private final DeleteProjectPhaseHandler handler;

  @DeleteMapping("/{phaseId}")
  public ResponseWrapper<Void> deleteProjectPhase(@PathVariable Long projectId, @PathVariable Long phaseId) {
    handler.execute(new DeleteProjectPhaseCommand(projectId, phaseId));
    return ResponseWrapper.deleted("ProjectPhase");
  }
}
