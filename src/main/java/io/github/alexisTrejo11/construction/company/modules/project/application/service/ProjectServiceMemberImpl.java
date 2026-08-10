package io.github.alexisTrejo11.construction.company.modules.project.application.service;

import io.github.alexisTrejo11.construction.company.modules.project.application.port.input.ProjectMemberService;
import io.github.alexisTrejo11.construction.company.modules.project.domain.model.ProjectMember;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.request.AddProjectMemberRequest;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.request.UpdateProjectMemberRoleRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceMemberImpl implements ProjectMemberService {


  @Override
  public List<ProjectMember> getProjectMembers(Long projectId) {
    return List.of();
  }

  @Override
  public ProjectMember addMemberToProject(Long projectId, AddProjectMemberRequest request) {
    return null;
  }

  @Override
  public ProjectMember updateProjectMemberRole(Long projectId, Long userId, UpdateProjectMemberRoleRequest request) {
    return null;
  }

  @Override
  public void removeMemberFromProject(Long projectId, Long userId) {

  }
}
