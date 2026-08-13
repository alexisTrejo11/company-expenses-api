package io.github.alexisTrejo11.construction.company.modules.contractor.features.updatestatus;

import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/contractors")
@RequiredArgsConstructor
@Tag(name = "Contractors", description = "Contractor and supplier catalog")
public class UpdateContractorStatusController {
  private final UpdateContractorStatusHandler handler;

  @PatchMapping("/{contractorId}/status")
  @Operation(summary = "Update contractor status", description = "Activates or deactivates a contractor (logical delete).")
  public ResponseEntity<ResponseWrapper<?>> updateStatus(
      @PathVariable Long contractorId,
      @RequestBody UpdateContractorStatusCommand request) {
    return ResponseEntity.ok(
        ResponseWrapper.success(handler.execute(contractorId, request), "Contractor status successfully updated")
    );
  }
}
