package io.github.alexisTrejo11.construction.company.modules.project.features.getsummary;

import io.github.alexisTrejo11.construction.company.shared.AppErrorResolver;
import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import io.github.alexisTrejo11.construction.company.shared.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/projects")
@RequiredArgsConstructor
public class GetProjectSummaryController {
  private final GetProjectSummaryHandler handler;

  @GetMapping("/{projectId}/summary")
  public ResponseEntity<ResponseWrapper<?>> getProjectSummary(@PathVariable Long projectId) {
    Result<GetProjectSummaryResponse> summaryResult = handler.execute(new GetProjectSummaryQuery(projectId));
    if (!summaryResult.isSuccess()) {
      return AppErrorResolver.handleResult(summaryResult);
    }

    return ResponseEntity.ok(ResponseWrapper.found(summaryResult.getData(), "Project", "ID", projectId));
  }
}
