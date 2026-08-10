package io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller;

import io.github.alexisTrejo11.construction.company.modules.project.application.port.input.ProjectService;
import io.github.alexisTrejo11.construction.company.modules.project.domain.model.ProjectEntity;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.request.CreateProjectRequest;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.request.SearchProjectRequest;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.request.UpdateProjectRequest;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.request.UpdateProjectStatusRequest;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.response.ProjectResponse;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.response.ProjectSummaryResponse;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.response.ProjectsGlobalSummaryResponse;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.mapper.ProjectMapper;
import io.github.alexisTrejo11.construction.company.shared.AppErrorResolver;
import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import io.github.alexisTrejo11.construction.company.shared.Result;
import io.github.alexisTrejo11.construction.company.shared.dto.PageRequest;
import io.github.alexisTrejo11.construction.company.shared.dto.auth.CurrentUser;
import io.github.alexisTrejo11.construction.company.shared.dto.auth.UserContext;
import io.github.alexisTrejo11.construction.company.shared.exception.AuthException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/api/projects")
@RequiredArgsConstructor
public class ProjectController {

  private final ProjectService projectService;
  private final ProjectMapper projectMapper;

  @GetMapping
  public ResponseWrapper<Page<ProjectResponse>> getProjects(@ModelAttribute @Valid SearchProjectRequest request) {
    Page<ProjectEntity> projectPage = projectService.getAllProjects(request);
    Page<ProjectResponse> projectResponsePage = projectPage.map(projectMapper::toResponse);
    return ResponseWrapper.found(projectResponsePage, "Project");
  }

  @PostMapping
  public ResponseEntity<ResponseWrapper<?>> createProject(@RequestBody @Valid CreateProjectRequest request) {
    Result<ProjectEntity> projectResult = projectService.createProject(request);
    if (!projectResult.isSuccess()) {
      var responseError = ResponseWrapper.applicationError(projectResult);
      return ResponseEntity.status(responseError.getCode()).body(responseError);
    }

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ResponseWrapper.created(projectResult.getData().getId(), "Project"));
  }

  @GetMapping("/my-projects")
  public ResponseWrapper<Page<ProjectResponse>> getMyProjects(
      @CurrentUser UserContext userContext,
      @ModelAttribute @Valid PageRequest request) {
    if (userContext == null) {
      throw new AuthException("User not present in context");
    }

    Page<ProjectEntity> projectPage = projectService.getProjectsByUserId(userContext.userId(), request.toPageable());
    Page<ProjectResponse> projectResponsePage = projectPage.map(projectMapper::toResponse);
    return ResponseWrapper.found(projectResponsePage, "Project");
  }

  @GetMapping("/summary")
  public ResponseWrapper<ProjectsGlobalSummaryResponse> getProjectsSummary() {
    ProjectsGlobalSummaryResponse summaryResponse = projectService.getGlobalSummary();
    return ResponseWrapper.found(summaryResponse, "Project");
  }

  @GetMapping("/{projectId}")
  public ResponseEntity<ResponseWrapper<?>> getProjectById(@PathVariable Long projectId) {
    Result<ProjectEntity> projectResult = projectService.getProjectById(projectId);
    if (!projectResult.isSuccess()) {
      return AppErrorResolver.handleResult(projectResult);
    }

    var projectResponse = projectMapper.toResponse(projectResult.getData());
    var successResponse = ResponseWrapper.found(projectResponse, "Project", "ID", projectId);
    return ResponseEntity.ok(successResponse);
  }

  @GetMapping("/code/{code}")
  public ResponseEntity<ResponseWrapper<?>> getProjectByCode(@PathVariable String code) {
    Result<ProjectEntity> projectResult = projectService.getProjectByCode(code);
    if (!projectResult.isSuccess()) {
      return AppErrorResolver.handleResult(projectResult);
    }

    var projectResponse = projectMapper.toResponse(projectResult.getData());
    var successResponse = ResponseWrapper.found(projectResponse, "Project", "Code", code);
    return ResponseEntity.ok(successResponse);
  }

  @PutMapping("/{projectId}")
  public ResponseWrapper<ProjectResponse> updateProject(
      @PathVariable Long projectId,
      @RequestBody @Valid UpdateProjectRequest request) {
    ProjectEntity project = projectService.updateProject(projectId, request);

    ProjectResponse projectResponse = projectMapper.toResponse(project);

    return ResponseWrapper.success(projectResponse, "Project successfully updated");
  }

  @DeleteMapping("/{projectId}")
  public ResponseWrapper<Void> deleteProject(@PathVariable Long projectId) {
    projectService.deleteProject(projectId);
    return ResponseWrapper.deleted("Project");
  }

  @GetMapping("/{projectId}/summary")
  public ResponseEntity<ResponseWrapper<?>> getProjectSummary(@PathVariable Long projectId) {
    Result<ProjectSummaryResponse> summaryResult = projectService.getProjectSummaryById(projectId);
    if (!summaryResult.isSuccess()) {
      return AppErrorResolver.handleResult(summaryResult);
    }

    var successResponse = ResponseWrapper.found(summaryResult.getData(), "Project", "ID", projectId);
    return ResponseEntity.ok(successResponse);
  }

  @PatchMapping("/{projectId}/status")
  public ResponseWrapper<ProjectResponse> updateProjectStatus(
      @PathVariable Long projectId,
      @RequestBody @Valid UpdateProjectStatusRequest request) {
    ProjectEntity project = projectService.updateProjectStatus(projectId, request);
    return ResponseWrapper.success(projectMapper.toResponse(project), "Project status successfully updated");
  }

  @PatchMapping("/{projectId}/restore")
  public ResponseWrapper<ProjectResponse> restoreProject(@PathVariable Long projectId) {
    ProjectEntity project = projectService.restoreProject(projectId);
    return ResponseWrapper.success(projectMapper.toResponse(project), "Project successfully restored");
  }
}
