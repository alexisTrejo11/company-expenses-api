package io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller;

import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/api/projects/{projectId}/members")
public class ProjectMembersController {

  @GetMapping
  public ResponseWrapper<?> getProjectMembers(@PathVariable Long projectId) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @PostMapping
  public ResponseWrapper<?> addProjectMember(@PathVariable Long projectId, @RequestBody Object requestBody) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @PutMapping("/{userId}")
  public ResponseWrapper<?> updateProjectMemberRole(
      @PathVariable Long projectId,
      @PathVariable Long userId,
      @RequestBody Object requestBody) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @DeleteMapping("/{userId}")
  public ResponseWrapper<?> removeProjectMember(@PathVariable Long projectId, @PathVariable Long userId) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
