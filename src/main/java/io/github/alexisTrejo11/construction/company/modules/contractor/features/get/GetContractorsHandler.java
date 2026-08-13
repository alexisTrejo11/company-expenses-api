package io.github.alexisTrejo11.construction.company.modules.contractor.features.get;

import io.github.alexisTrejo11.construction.company.modules.contractor.shared.dto.ContractorResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class GetContractorsHandler {
  public Page<ContractorResponse> execute(GetContractorsQuery query) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
