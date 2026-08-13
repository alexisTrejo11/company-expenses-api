package io.github.alexisTrejo11.construction.company.modules.project.features.delete;

import io.github.alexisTrejo11.construction.company.shared.AppErrorResolver;
import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import io.github.alexisTrejo11.construction.company.shared.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/projects")
@RequiredArgsConstructor
public class DeleteProjectController {
  private final DeleteProjectHandler handler;

  @DeleteMapping("/{projectId}")
  public ResponseEntity<ResponseWrapper<?>> deleteProject(@PathVariable Long projectId) {
    Result<Void> deleteResult = handler.execute(new DeleteProjectCommand(projectId));
    if (!deleteResult.isSuccess()) {
      return AppErrorResolver.handleResult(deleteResult);
    }

    return ResponseEntity.ok(ResponseWrapper.deleted("Project"));
  }
}
