package io.github.alexisTrejo11.construction.company.modules.approval.features.getbytarget;

import io.github.alexisTrejo11.construction.company.modules.approval.shared.domain.ApprovalTargetType;
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
@RequestMapping("/v2/api/approvals/targets")
@RequiredArgsConstructor
@Tag(name = "Approvals", description = "Polymorphic approval workflow")
public class GetApprovalsByTargetController {
  private final GetApprovalsByTargetHandler handler;

  @GetMapping("/{targetType}/{targetId}")
  @Operation(summary = "Get approval history", description = "Returns the approval audit history for a specific resource.")
  public ResponseEntity<ResponseWrapper<?>> getByTarget(
      @PathVariable ApprovalTargetType targetType,
      @PathVariable Long targetId) {
    return ResponseEntity.ok(ResponseWrapper.found(
        handler.execute(new GetApprovalsByTargetQuery(targetType, targetId)),
        "Approval"
    ));
  }
}
