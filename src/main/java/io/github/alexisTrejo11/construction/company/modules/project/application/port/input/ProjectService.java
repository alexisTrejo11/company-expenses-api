package io.github.alexisTrejo11.construction.company.modules.project.application.port.input;

import io.github.alexisTrejo11.construction.company.modules.project.domain.model.ProjectEntity;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.request.CreateProjectRequest;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.request.SearchProjectRequest;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.request.UpdateProjectRequest;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.request.UpdateProjectStatusRequest;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.response.ProjectSummaryResponse;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.response.ProjectsGlobalSummaryResponse;
import io.github.alexisTrejo11.construction.company.shared.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {

  Result<ProjectEntity> createProject(CreateProjectRequest request);

  Result<ProjectEntity> getProjectById(Long id);

  Result<ProjectEntity> getProjectByCode(String code);

  Page<ProjectEntity> getAllProjects(SearchProjectRequest request);

  Page<ProjectEntity> getProjectsByUserId(Long userId, Pageable pageable);

  ProjectsGlobalSummaryResponse getGlobalSummary();

  Result<ProjectSummaryResponse> getProjectSummaryById(Long id);

  ProjectEntity updateProject(Long id, UpdateProjectRequest request);

  ProjectEntity updateProjectStatus(Long id, UpdateProjectStatusRequest request);

  void deleteProject(Long id);

  ProjectEntity restoreProject(Long id);
}