package io.github.alexisTrejo11.construction.company.modules.budget.shared.persistence;

import io.github.alexisTrejo11.construction.company.modules.budget.shared.domain.ExpenseCategory;
import io.github.alexisTrejo11.construction.company.modules.budget.shared.persistence.entity.BudgetItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BudgetItemRepository extends JpaRepository<BudgetItemEntity, Long> {

  @Query("""
        SELECT i FROM BudgetItemEntity i
        WHERE i.budget.id = :budgetId
          AND i.deletedAt IS NULL
          AND (:category IS NULL OR i.category = :category)
          AND (:phaseId IS NULL OR i.phase.id = :phaseId)
    """)
  Page<BudgetItemEntity> findWithFilters(
      @Param("budgetId") Long budgetId,
      @Param("category") ExpenseCategory category,
      @Param("phaseId") Long phaseId,
      Pageable pageable
  );
}
