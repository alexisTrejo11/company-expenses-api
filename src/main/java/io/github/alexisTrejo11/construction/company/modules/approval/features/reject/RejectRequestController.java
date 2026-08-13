package io.github.alexisTrejo11.construction.company.modules.approval.features.reject;

import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/approvals")
@RequiredArgsConstructor
@Tag(name = "Approvals", description = "Polymorphic approval workflow")
public class RejectRequestController {
  private final RejectRequestHandler handler;

  @PostMapping("/{approvalId}/reject")
  @Operation(summary = "Reject request", description = "Rejects the request. A reason/comment is required.")
  public ResponseEntity<ResponseWrapper<?>> reject(
      @PathVariable Long approvalId,
      @RequestBody RejectRequestCommand request) {
    return ResponseEntity.ok(
        ResponseWrapper.success(handler.execute(approvalId, request), "Approval successfully rejected")
    );
  }
}
