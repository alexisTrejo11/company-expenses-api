package io.github.alexisTrejo11.construction.company.modules.contractor.features.getbyid;

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
@RequestMapping("/v2/api/contractors")
@RequiredArgsConstructor
@Tag(name = "Contractors", description = "Contractor and supplier catalog")
public class GetContractorByIdController {
  private final GetContractorByIdHandler handler;

  @GetMapping("/{contractorId}")
  @Operation(summary = "Get contractor", description = "Returns contractor details, contact data and tax identification.")
  public ResponseEntity<ResponseWrapper<?>> getContractor(@PathVariable Long contractorId) {
    return ResponseEntity.ok(ResponseWrapper.found(
        handler.execute(new GetContractorByIdQuery(contractorId)),
        "Contractor",
        "ID",
        contractorId
    ));
  }
}
