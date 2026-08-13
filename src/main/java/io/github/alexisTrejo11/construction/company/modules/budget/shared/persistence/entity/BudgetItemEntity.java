package io.github.alexisTrejo11.construction.company.modules.budget.shared.persistence.entity;

import io.github.alexisTrejo11.construction.company.modules.budget.shared.domain.ExpenseCategory;
import io.github.alexisTrejo11.construction.company.modules.budget.shared.domain.Money;
import io.github.alexisTrejo11.construction.company.modules.project.shared.persistence.entity.ProjectPhase;
import io.github.alexisTrejo11.construction.company.shared.jpa.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "budget_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetItemEntity extends BaseModel {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "budget_id", nullable = false)
  private BudgetEntity budget;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "phase_id")
  private ProjectPhase phase;

  @Column(nullable = false, length = 255)
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 30)
  private ExpenseCategory category;

  @Column(nullable = false, length = 20)
  private String unit;

  @Column(name = "planned_quantity", nullable = false, precision = 12, scale = 4)
  private BigDecimal plannedQuantity;

  @Column(name = "unit_price", nullable = false, precision = 15, scale = 2)
  private BigDecimal unitPrice;

  @Column(name = "planned_total", nullable = false, precision = 15, scale = 2)
  private BigDecimal plannedTotal;

  @Column(name = "executed_total", nullable = false, precision = 15, scale = 2)
  @Builder.Default
  private BigDecimal executedTotal = BigDecimal.ZERO;

  public void addExpenseAmount(Money amount) {
    this.executedTotal = this.executedTotal.add(amount.amount());
  }

  public void updateAllocation(String description, ExpenseCategory category, String unit,
      BigDecimal plannedQuantity, BigDecimal unitPrice) {
    this.description = description;
    this.category = category;
    this.unit = unit;
    this.plannedQuantity = plannedQuantity;
    this.unitPrice = unitPrice;
    this.plannedTotal = unitPrice.multiply(plannedQuantity);
  }
}
