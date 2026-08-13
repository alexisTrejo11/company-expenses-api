package io.github.alexisTrejo11.construction.company.modules.budget.shared.persistence;

import io.github.alexisTrejo11.construction.company.modules.budget.shared.persistence.entity.BudgetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetRepository extends JpaRepository<BudgetEntity, Long> {

  Optional<BudgetEntity> findByProject_Id(Long projectId);

  boolean existsByProject_Id(Long projectId);
}
