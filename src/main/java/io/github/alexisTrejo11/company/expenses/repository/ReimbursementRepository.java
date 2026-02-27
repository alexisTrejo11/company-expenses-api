package io.github.alexisTrejo11.company.expenses.repository;

import io.github.alexisTrejo11.company.expenses.model.Reimbursement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReimbursementRepository extends JpaRepository<Reimbursement, Long> {
    Page<Reimbursement> findByProcessedBy_Id(Long processedBy, Pageable pageable);
}
