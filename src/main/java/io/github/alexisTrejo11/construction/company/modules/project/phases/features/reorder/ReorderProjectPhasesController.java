package io.github.alexisTrejo11.construction.company.modules.project.phases.features.reorder;

import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/projects/{projectId}/phases")
@RequiredArgsConstructor
public class ReorderProjectPhasesController {
  private final ReorderProjectPhasesHandler handler;

  @PatchMapping("/reorder")
  public ResponseWrapper<Void> reorderProjectPhases(
      @PathVariable Long projectId,
      @RequestBody @Valid ReorderProjectPhasesCommand request) {
    handler.execute(projectId, request);
    return ResponseWrapper.success("Project phases successfully reordered");
  }
}
