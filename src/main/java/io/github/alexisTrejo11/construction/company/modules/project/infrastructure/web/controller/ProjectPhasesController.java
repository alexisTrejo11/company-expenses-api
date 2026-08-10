package io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller;

import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/api/projects/{projectId}/phases")
public class ProjectPhasesController {

  @GetMapping
  public ResponseWrapper<?> getProjectPhases(@PathVariable Long projectId) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @PostMapping
  public ResponseWrapper<?> addProjectPhase(@PathVariable Long projectId, @RequestBody Object requestBody) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @GetMapping("/{phaseId}")
  public ResponseWrapper<?> getProjectPhase(@PathVariable Long projectId, @PathVariable Long phaseId) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @PutMapping("/{phaseId}")
  public ResponseWrapper<?> updateProjectPhase(
      @PathVariable Long projectId,
      @PathVariable Long phaseId,
      @RequestBody Object requestBody) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @DeleteMapping("/{phaseId}")
  public ResponseWrapper<?> deleteProjectPhase(@PathVariable Long projectId, @PathVariable Long phaseId) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @PatchMapping("/reorder")
  public ResponseWrapper<?> reorderProjectPhases(@PathVariable Long projectId, @RequestBody Object requestBody) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
