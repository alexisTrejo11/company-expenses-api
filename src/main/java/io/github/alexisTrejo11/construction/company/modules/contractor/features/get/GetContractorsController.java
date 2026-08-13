package io.github.alexisTrejo11.construction.company.modules.contractor.features.get;

import io.github.alexisTrejo11.construction.company.modules.contractor.shared.domain.ContractorStatus;
import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import io.github.alexisTrejo11.construction.company.shared.dto.PageRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/contractors")
@RequiredArgsConstructor
@Tag(name = "Contractors", description = "Contractor and supplier catalog")
public class GetContractorsController {
  private final GetContractorsHandler handler;

  @GetMapping
  @Operation(summary = "List contractors", description = "Paginated contractor list filtered by specialty and status.")
  public ResponseEntity<ResponseWrapper<?>> getContractors(
      @ModelAttribute PageRequest pageRequest,
      @RequestParam(required = false) String specialty,
      @RequestParam(required = false) ContractorStatus status) {
    return ResponseEntity.ok(ResponseWrapper.found(
        handler.execute(new GetContractorsQuery(specialty, status, pageRequest)),
        "Contractor"
    ));
  }
}
