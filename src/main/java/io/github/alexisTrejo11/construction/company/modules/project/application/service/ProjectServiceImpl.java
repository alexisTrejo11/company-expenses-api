package io.github.alexisTrejo11.construction.company.modules.project.application.service;

import io.github.alexisTrejo11.construction.company.modules.project.application.port.input.ProjectService;
import io.github.alexisTrejo11.construction.company.modules.project.domain.model.ProjectEntity;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.persistence.repository.ProjectRepository;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.request.CreateProjectRequest;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.request.SearchProjectRequest;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.request.UpdateProjectRequest;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.request.UpdateProjectStatusRequest;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.response.ProjectSummaryResponse;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.response.ProjectsGlobalSummaryResponse;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.mapper.ProjectMapper;
import io.github.alexisTrejo11.construction.company.shared.Result;
import io.github.alexisTrejo11.construction.company.shared.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
  private final ProjectRepository repository;
  private final ProjectMapper mapper;

  @Override
  public Result<ProjectEntity> createProject(CreateProjectRequest request) {
    if (repository.existsByCode(request.code())) {
        return Result.conflict("Code already exists");
    }

    ProjectEntity entity = mapper.toEntity(request);

    Result<Void> validateResult =  entity.validate();
    if (!validateResult.isSuccess()) {
      return Result.business(validateResult.getErrorMessage());
    }

    ProjectEntity savedEntity = repository.save(entity);
    return Result.success(savedEntity);
  }

  @Override
  @Transactional(readOnly = true)
  public Result<ProjectEntity> getProjectById(Long id) {
    var optionalProject = repository.findById(id);
    return optionalProject
        .map(Result::success)
        .orElseGet(() -> Result.business("Project not found"));
  }

  @Override
  public Result<ProjectEntity> getProjectByCode(String code) {
    var optionalProject = repository.findByCode(code);
    return optionalProject
        .map(Result::success)
        .orElseGet(() -> Result.business("Project not found"));  }


  @Override
  public Page<ProjectEntity> getAllProjects(SearchProjectRequest query) {
    return repository.findWithFilters(
        query.search(),
        query.status(),
        query.city(),
        query.pageRequest().toPageable()
    );
  }

  @Override
  public Page<ProjectEntity> getProjectsByUserId(Long userId, Pageable pageable) {
      return repository.findByUserIdWithMembers(userId, pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public ProjectsGlobalSummaryResponse getGlobalSummary() {
    return repository.getGlobalSummary();
  }

  @Override
  @Transactional(readOnly = true)
  public Result<ProjectSummaryResponse> getProjectSummaryById(Long id) {
    return repository.findById(id)
        .map(mapper::toSummaryResponse)
        .map(Result::success)
        .orElseGet(() -> Result.business("Project not found"));
  }

  @Override
  public ProjectEntity updateProject(Long id, UpdateProjectRequest request) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public ProjectEntity updateProjectStatus(Long id, UpdateProjectStatusRequest request) {
    ProjectEntity project = repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Project Not Found"));

    project.updateStatus(request.status());
    return repository.save(project);
  }

  @Override
  public void deleteProject(Long id) {
    ProjectEntity project = repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Project Not Found"));

    project.delete();
    repository.delete(project);
  }

  @Override
  public ProjectEntity restoreProject(Long id) {
    ProjectEntity project = repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Project Not Found"));

    project.restore();
    return repository.save(project);
  }
}
