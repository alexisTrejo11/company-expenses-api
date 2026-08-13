package io.github.alexisTrejo11.construction.company.modules.project.features.create;

import io.github.alexisTrejo11.construction.company.modules.project.shared.persistence.ProjectRepository;
import io.github.alexisTrejo11.construction.company.modules.project.shared.persistence.entity.ProjectEntity;
import io.github.alexisTrejo11.construction.company.shared.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateProjectHandler {
  private final ProjectRepository repository;
  private final CreateProjectMapper mapper;

  @Transactional
  public Result<CreateProjectResponse> execute(CreateProjectCommand request) {
    if (repository.existsByCode(request.code())) {
      return Result.conflict("Code already exists");
    }

    ProjectEntity entity = mapper.toEntity(request);

    Result<Void> validateResult = entity.validate();
    if (!validateResult.isSuccess()) {
      return Result.business(validateResult.getErrorMessage());
    }

    ProjectEntity savedEntity = repository.save(entity);
    return Result.success(new CreateProjectResponse(savedEntity.getId()));
  }
}
