package io.github.alexisTrejo11.construction.company.modules.project.members.features.remove;

import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/projects/{projectId}/members")
@RequiredArgsConstructor
public class RemoveProjectMemberController {
  private final RemoveProjectMemberHandler handler;

  @DeleteMapping("/{userId}")
  public ResponseWrapper<Void> removeProjectMember(@PathVariable Long projectId, @PathVariable Long userId) {
    handler.execute(new RemoveProjectMemberCommand(projectId, userId));
    return ResponseWrapper.deleted("ProjectMember");
  }
}
