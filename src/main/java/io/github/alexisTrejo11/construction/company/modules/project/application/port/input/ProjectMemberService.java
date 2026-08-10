package io.github.alexisTrejo11.construction.company.modules.project.application.port.input;

import io.github.alexisTrejo11.construction.company.modules.project.domain.model.ProjectMember;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.request.AddProjectMemberRequest;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.request.UpdateProjectMemberRoleRequest;

import java.util.List;

public interface ProjectMemberService {
  List<ProjectMember> getProjectMembers(Long projectId);

  ProjectMember addMemberToProject(Long projectId, AddProjectMemberRequest request);

  ProjectMember updateProjectMemberRole(Long projectId, Long userId, UpdateProjectMemberRoleRequest request);

  void removeMemberFromProject(Long projectId, Long userId);
}
