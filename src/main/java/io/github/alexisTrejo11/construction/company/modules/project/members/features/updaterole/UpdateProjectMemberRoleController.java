package io.github.alexisTrejo11.construction.company.modules.project.members.features.updaterole;

import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/projects/{projectId}/members")
@RequiredArgsConstructor
public class UpdateProjectMemberRoleController {
  private final UpdateProjectMemberRoleHandler handler;

  @PutMapping("/{userId}")
  public ResponseWrapper<?> updateProjectMemberRole(
      @PathVariable Long projectId,
      @PathVariable Long userId,
      @RequestBody @Valid UpdateProjectMemberRoleCommand request) {
    return ResponseWrapper.success(
        handler.execute(projectId, userId, request),
        "Project member role successfully updated"
    );
  }
}
