package io.github.alexisTrejo11.construction.company.modules.project.features.getmyprojects;

import io.github.alexisTrejo11.construction.company.modules.project.shared.dto.ProjectResponse;
import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import io.github.alexisTrejo11.construction.company.shared.dto.PageRequest;
import io.github.alexisTrejo11.construction.company.shared.dto.auth.CurrentUser;
import io.github.alexisTrejo11.construction.company.shared.dto.auth.UserContext;
import io.github.alexisTrejo11.construction.company.shared.exception.AuthException;
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
public class GetMyProjectsController {
  private final GetMyProjectsHandler handler;

  @GetMapping("/my-projects")
  public ResponseWrapper<Page<ProjectResponse>> getMyProjects(
      @CurrentUser UserContext userContext,
      @ModelAttribute @Valid PageRequest request) {
    if (userContext == null) {
      throw new AuthException("User not present in context");
    }

    return ResponseWrapper.found(
        handler.execute(new GetMyProjectsQuery(userContext.userId(), request)),
        "Project"
    );
  }
}
