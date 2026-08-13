package io.github.alexisTrejo11.construction.company.modules.approval.features.reject;

import io.github.alexisTrejo11.construction.company.modules.approval.shared.dto.ApprovalResponse;
import org.springframework.stereotype.Service;

@Service
public class RejectRequestHandler {
  public ApprovalResponse execute(Long approvalId, RejectRequestCommand command) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
