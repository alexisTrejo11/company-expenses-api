package io.github.alexisTrejo11.construction.company.modules.budget.shared.persistence.entity;

import io.github.alexisTrejo11.construction.company.modules.budget.shared.domain.BudgetStatus;
import io.github.alexisTrejo11.construction.company.modules.budget.shared.domain.Money;
import io.github.alexisTrejo11.construction.company.modules.expense.shared.persistence.entity.ExpenseEntity;
import io.github.alexisTrejo11.construction.company.modules.project.shared.persistence.entity.ProjectEntity;
import io.github.alexisTrejo11.construction.company.shared.jpa.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "budgets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetEntity extends BaseModel {

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id", nullable = false, unique = true)
  @ToString.Exclude
  private ProjectEntity project;

  @Column(name = "estimated_amount", nullable = false, precision = 15, scale = 2)
  private BigDecimal estimatedAmount;

  @Column(name = "executed_amount", nullable = false, precision = 15, scale = 2)
  @Builder.Default
  private BigDecimal executedAmount = BigDecimal.ZERO;

  @Column(length = 3, nullable = false)
  @Builder.Default
  private String currency = "MXN";

  @Column(length = 500)
  private String notes;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  @Builder.Default
  private BudgetStatus status = BudgetStatus.DRAFT;

  @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<BudgetItemEntity> items = new ArrayList<>();

  @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<ExpenseEntity> expenses = new ArrayList<>();

  public void addLineItem(BudgetItemEntity item) {
    ensureBudgetIsOpen();
    items.add(item);
    item.setBudget(this);
  }

  public void recordExpense(ExpenseEntity expense) {
    ensureBudgetIsOpen();
    expenses.add(expense);
    expense.setBudget(this);
    executedAmount = executedAmount.add(expense.getAmount());
  }

  public Money getRemainingBalance() {
    return targetAmount().subtract(executedTotal());
  }

  public boolean isOverBudget() {
    return executedTotal().isGreaterThan(targetAmount());
  }

  public void updateTargetAmount(Money newTargetAmount) {
    ensureBudgetIsOpen();
    this.estimatedAmount = newTargetAmount.amount();
    this.currency = newTargetAmount.currency();
  }

  public void approve() {
    if (this.status != BudgetStatus.DRAFT) {
      throw new IllegalStateException("Only DRAFT budgets can be approved");
    }
    this.status = BudgetStatus.APPROVED;
  }

  private Money targetAmount() {
    return Money.of(estimatedAmount, currency);
  }

  private Money executedTotal() {
    return Money.of(executedAmount, currency);
  }

  private void ensureBudgetIsOpen() {
    if (this.status == BudgetStatus.CLOSED) {
      throw new IllegalStateException("Cannot modify a CLOSED budget");
    }
  }
}
