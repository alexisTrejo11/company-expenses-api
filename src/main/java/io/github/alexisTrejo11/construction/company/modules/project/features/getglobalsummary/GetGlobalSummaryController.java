package io.github.alexisTrejo11.construction.company.modules.project.features.getglobalsummary;

import io.github.alexisTrejo11.construction.company.modules.project.shared.dto.ProjectsGlobalSummaryResponse;
import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/projects")
@RequiredArgsConstructor
public class GetGlobalSummaryController {
  private final GetGlobalSummaryHandler handler;

  @GetMapping("/summary")
  public ResponseWrapper<ProjectsGlobalSummaryResponse> getProjectsSummary() {
    return ResponseWrapper.found(handler.execute(), "Project");
  }
}
