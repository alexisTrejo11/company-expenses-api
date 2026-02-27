package io.github.alexisTrejo11.company.expenses.service.Interfaces;

import io.github.alexisTrejo11.company.expenses.dto.Reimbursement.ReimbursementDTO;
import io.github.alexisTrejo11.company.expenses.dto.Reimbursement.ReimbursementInsertDTO;
import io.github.alexisTrejo11.company.expenses.shared.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReimbursementService {
    ReimbursementDTO getReimbursementById(Long reimbursementId);
    Page<ReimbursementDTO> getReimbursementByUserId(Long userId, Pageable pageable);

    Result<Void> validate(ReimbursementInsertDTO insertDTO);
    ReimbursementDTO createReimbursement(ReimbursementInsertDTO reimbursementInsertDTO, String email);

}
