package io.github.alexisTrejo11.construction.company.modules.contractor.features.getprojects;

import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import io.github.alexisTrejo11.construction.company.shared.dto.PageRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/contractors")
@RequiredArgsConstructor
@Tag(name = "Contractors", description = "Contractor and supplier catalog")
public class GetContractorProjectsController {
  private final GetContractorProjectsHandler handler;

  @GetMapping("/{contractorId}/projects")
  @Operation(summary = "List contractor projects", description = "Lists the project history assigned to a contractor.")
  public ResponseEntity<ResponseWrapper<?>> getProjects(
      @PathVariable Long contractorId,
      @ModelAttribute PageRequest pageRequest) {
    return ResponseEntity.ok(ResponseWrapper.found(
        handler.execute(new GetContractorProjectsQuery(contractorId, pageRequest)),
        "ContractorProject"
    ));
  }
}
