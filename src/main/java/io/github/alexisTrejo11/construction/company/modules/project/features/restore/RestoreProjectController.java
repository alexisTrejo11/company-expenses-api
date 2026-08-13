package io.github.alexisTrejo11.construction.company.modules.project.features.restore;

import io.github.alexisTrejo11.construction.company.modules.project.shared.dto.ProjectResponse;
import io.github.alexisTrejo11.construction.company.shared.AppErrorResolver;
import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import io.github.alexisTrejo11.construction.company.shared.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/projects")
@RequiredArgsConstructor
public class RestoreProjectController {
  private final RestoreProjectHandler handler;

  @PatchMapping("/{projectId}/restore")
  public ResponseEntity<ResponseWrapper<?>> restoreProject(@PathVariable Long projectId) {
    Result<ProjectResponse> projectResult = handler.execute(new RestoreProjectCommand(projectId));
    if (!projectResult.isSuccess()) {
      return AppErrorResolver.handleResult(projectResult);
    }

    return ResponseEntity.ok(ResponseWrapper.success(projectResult.getData(), "Project successfully restored"));
  }
}
