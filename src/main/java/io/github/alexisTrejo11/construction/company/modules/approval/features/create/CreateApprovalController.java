package io.github.alexisTrejo11.construction.company.modules.approval.features.create;

import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/approvals")
@RequiredArgsConstructor
@Tag(name = "Approvals", description = "Polymorphic approval workflow")
public class CreateApprovalController {
  private final CreateApprovalHandler handler;

  @PostMapping
  @Operation(summary = "Create approval request", description = "Creates an approval request for a resource (EXPENSE, BUDGET_CHANGE, etc.).")
  public ResponseEntity<ResponseWrapper<?>> create(@RequestBody CreateApprovalCommand request) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ResponseWrapper.created(handler.execute(request), "Approval"));
  }
}
