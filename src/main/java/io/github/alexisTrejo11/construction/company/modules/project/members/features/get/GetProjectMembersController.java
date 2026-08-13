package io.github.alexisTrejo11.construction.company.modules.project.members.features.get;

import io.github.alexisTrejo11.construction.company.modules.project.shared.dto.ProjectMemberResponse;
import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v2/api/projects/{projectId}/members")
@RequiredArgsConstructor
public class GetProjectMembersController {
  private final GetProjectMembersHandler handler;

  @GetMapping
  public ResponseWrapper<List<ProjectMemberResponse>> getProjectMembers(@PathVariable Long projectId) {
    return ResponseWrapper.found(handler.execute(new GetProjectMembersQuery(projectId)), "ProjectMember");
  }
}
