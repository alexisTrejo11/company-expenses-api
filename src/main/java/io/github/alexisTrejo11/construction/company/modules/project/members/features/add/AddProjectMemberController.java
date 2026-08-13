package io.github.alexisTrejo11.construction.company.modules.project.members.features.add;

import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/projects/{projectId}/members")
@RequiredArgsConstructor
public class AddProjectMemberController {
  private final AddProjectMemberHandler handler;

  @PostMapping
  public ResponseEntity<ResponseWrapper<?>> addProjectMember(
      @PathVariable Long projectId,
      @RequestBody @Valid AddProjectMemberCommand request) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ResponseWrapper.created(handler.execute(projectId, request), "ProjectMember"));
  }
}
