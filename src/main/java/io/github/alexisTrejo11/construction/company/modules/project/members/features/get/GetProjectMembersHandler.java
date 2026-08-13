package io.github.alexisTrejo11.construction.company.modules.project.members.features.get;

import io.github.alexisTrejo11.construction.company.modules.project.shared.dto.ProjectMemberResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetProjectMembersHandler {
  public List<ProjectMemberResponse> execute(GetProjectMembersQuery query) {
    return List.of();
  }
}
