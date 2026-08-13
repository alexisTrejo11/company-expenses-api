package io.github.alexisTrejo11.construction.company.modules.contractor.features.update;

import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/contractors")
@RequiredArgsConstructor
@Tag(name = "Contractors", description = "Contractor and supplier catalog")
public class UpdateContractorController {
  private final UpdateContractorHandler handler;

  @PutMapping("/{contractorId}")
  @Operation(summary = "Update contractor", description = "Updates tax data, specialty or contractor contacts.")
  public ResponseEntity<ResponseWrapper<?>> update(
      @PathVariable Long contractorId,
      @RequestBody UpdateContractorCommand request) {
    return ResponseEntity.ok(
        ResponseWrapper.success(handler.execute(contractorId, request), "Contractor successfully updated")
    );
  }
}
