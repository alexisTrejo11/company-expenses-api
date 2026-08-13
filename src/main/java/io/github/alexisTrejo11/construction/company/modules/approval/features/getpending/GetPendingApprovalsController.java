package io.github.alexisTrejo11.construction.company.modules.approval.features.getpending;

import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import io.github.alexisTrejo11.construction.company.shared.dto.PageRequest;
import io.github.alexisTrejo11.construction.company.shared.dto.auth.CurrentUser;
import io.github.alexisTrejo11.construction.company.shared.dto.auth.UserContext;
import io.github.alexisTrejo11.construction.company.shared.exception.AuthException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/approvals")
@RequiredArgsConstructor
@Tag(name = "Approvals", description = "Polymorphic approval workflow")
public class GetPendingApprovalsController {
  private final GetPendingApprovalsHandler handler;

  @GetMapping("/pending")
  @Operation(summary = "List pending approvals", description = "Lists pending approval requests assigned to the current user.")
  public ResponseEntity<ResponseWrapper<?>> getPending(
      @CurrentUser UserContext userContext,
      @ModelAttribute PageRequest pageRequest) {
    if (userContext == null) {
      throw new AuthException("User not present in context");
    }

    return ResponseEntity.ok(ResponseWrapper.found(
        handler.execute(new GetPendingApprovalsQuery(userContext.userId(), pageRequest)),
        "Approval"
    ));
  }
}
