package io.github.alexisTrejo11.construction.company.modules.approval.features.getpending;

import io.github.alexisTrejo11.construction.company.modules.approval.shared.dto.ApprovalResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class GetPendingApprovalsHandler {
  public Page<ApprovalResponse> execute(GetPendingApprovalsQuery query) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
