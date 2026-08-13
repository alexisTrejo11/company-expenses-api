package io.github.alexisTrejo11.construction.company.modules.approval.features.approve;

import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/approvals")
@RequiredArgsConstructor
@Tag(name = "Approvals", description = "Polymorphic approval workflow")
public class ApproveRequestController {
  private final ApproveRequestHandler handler;

  @PostMapping("/{approvalId}/approve")
  @Operation(summary = "Approve request", description = "Approves the request and records the reviewer timestamp.")
  public ResponseEntity<ResponseWrapper<?>> approve(@PathVariable Long approvalId) {
    return ResponseEntity.ok(
        ResponseWrapper.success(handler.execute(new ApproveRequestCommand(approvalId)), "Approval successfully approved")
    );
  }
}
