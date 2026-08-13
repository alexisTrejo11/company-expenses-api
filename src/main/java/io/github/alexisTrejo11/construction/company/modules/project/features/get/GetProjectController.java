package io.github.alexisTrejo11.construction.company.modules.project.features.get;

import io.github.alexisTrejo11.construction.company.modules.project.shared.dto.ProjectResponse;
import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/projects")
@RequiredArgsConstructor
public class GetProjectController {
  private final GetProjectHandler handler;

  @GetMapping
  public ResponseWrapper<Page<ProjectResponse>> getProjects(@ModelAttribute @Valid GetProjectQuery request) {
    return ResponseWrapper.found(handler.execute(request), "Project");
  }
}
