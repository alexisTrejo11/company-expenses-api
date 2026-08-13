package io.github.alexisTrejo11.construction.company.modules.expense.shared.persistence;

import io.github.alexisTrejo11.construction.company.modules.expense.shared.domain.ExpenseStatus;
import io.github.alexisTrejo11.construction.company.modules.expense.shared.persistence.entity.ExpenseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

  @Query("""
        SELECT e FROM ExpenseEntity e
        WHERE e.deletedAt IS NULL
          AND (:projectId IS NULL OR e.budget.project.id = :projectId)
          AND (:budgetId IS NULL OR e.budget.id = :budgetId)
          AND (:vendorName IS NULL OR LOWER(e.vendorName) LIKE LOWER(CONCAT('%', :vendorName, '%')))
          AND (:status IS NULL OR e.status = :status)
    """)
  Page<ExpenseEntity> findWithFilters(
      @Param("projectId") Long projectId,
      @Param("budgetId") Long budgetId,
      @Param("vendorName") String vendorName,
      @Param("status") ExpenseStatus status,
      Pageable pageable
  );

  @Query("""
        SELECT e FROM ExpenseEntity e
        WHERE e.budget.id = :budgetId
          AND e.deletedAt IS NULL
          AND (:itemId IS NULL OR e.budgetItem.id = :itemId)
          AND (:startDate IS NULL OR e.expenseDate >= :startDate)
          AND (:endDate IS NULL OR e.expenseDate <= :endDate)
    """)
  Page<ExpenseEntity> findByBudgetWithFilters(
      @Param("budgetId") Long budgetId,
      @Param("itemId") Long itemId,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate,
      Pageable pageable
  );

  Optional<ExpenseEntity> findByIdAndBudget_Id(Long expenseId, Long budgetId);
}
