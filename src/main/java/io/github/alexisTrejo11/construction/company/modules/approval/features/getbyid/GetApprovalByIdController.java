package io.github.alexisTrejo11.construction.company.modules.approval.features.getbyid;

import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/approvals")
@RequiredArgsConstructor
@Tag(name = "Approvals", description = "Polymorphic approval workflow")
public class GetApprovalByIdController {
  private final GetApprovalByIdHandler handler;

  @GetMapping("/{approvalId}")
  @Operation(summary = "Get approval", description = "Returns approval details, current status and comments.")
  public ResponseEntity<ResponseWrapper<?>> getApproval(@PathVariable Long approvalId) {
    return ResponseEntity.ok(ResponseWrapper.found(
        handler.execute(new GetApprovalByIdQuery(approvalId)),
        "Approval",
        "ID",
        approvalId
    ));
  }
}
