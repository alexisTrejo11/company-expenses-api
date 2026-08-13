package io.github.alexisTrejo11.construction.company.modules.budget.features.create;

import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/v2/api/projects/{projectId}/budget")
@RequiredArgsConstructor
@Tag(name = "Budget", description = "Core budget operations")
public class CreateBudgetController {
  private final CreateBudgetHandler handler;

  @PostMapping
  @Operation(summary = "Initialize project budget", description = "Initializes a master budget for a project.")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Budget created")
  })
  public ResponseEntity<ResponseWrapper<?>> createProjectBudget(
      @PathVariable Long projectId,
      @RequestBody @Valid CreateBudgetCommand request) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ResponseWrapper.created(handler.execute(projectId, request), "Budget"));
  }
}
