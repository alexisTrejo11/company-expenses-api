package io.github.alexisTrejo11.construction.company.modules.expense.shared.persistence.entity;

import io.github.alexisTrejo11.construction.company.modules.budget.shared.persistence.entity.BudgetEntity;
import io.github.alexisTrejo11.construction.company.modules.budget.shared.persistence.entity.BudgetItemEntity;
import io.github.alexisTrejo11.construction.company.modules.expense.shared.domain.ExpenseStatus;
import io.github.alexisTrejo11.construction.company.shared.jpa.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "expenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseEntity extends BaseModel {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "budget_id", nullable = false)
  private BudgetEntity budget;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "budget_item_id", nullable = false)
  private BudgetItemEntity budgetItem;

  @Column(nullable = false, length = 255)
  private String description;

  @Column(nullable = false, precision = 15, scale = 2)
  private BigDecimal amount;

  @Column(length = 3, nullable = false)
  @Builder.Default
  private String currency = "MXN";

  @Column(name = "expense_date", nullable = false)
  private LocalDate expenseDate;

  @Column(name = "invoice_url", length = 500)
  private String invoiceUrl;

  @Column(name = "receipt_number", length = 100)
  private String receiptNumber;

  @Column(name = "vendor_tax_id", length = 50)
  private String vendorTaxId;

  @Column(name = "vendor_name", length = 150)
  private String vendorName;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 30)
  @Builder.Default
  private ExpenseStatus status = ExpenseStatus.DRAFT;

  @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<ExpenseAttachmentEntity> attachments = new ArrayList<>();

  public void submitForApproval() {
    ensureDraft();
    this.status = ExpenseStatus.PENDING_APPROVAL;
  }

  public void approve() {
    if (this.status != ExpenseStatus.PENDING_APPROVAL) {
      throw new IllegalStateException("Expense must be pending approval");
    }
    this.status = ExpenseStatus.APPROVED;
  }

  public void reject() {
    if (this.status != ExpenseStatus.PENDING_APPROVAL) {
      throw new IllegalStateException("Expense must be pending approval");
    }
    this.status = ExpenseStatus.REJECTED;
  }

  public void ensureDraft() {
    if (this.status != ExpenseStatus.DRAFT) {
      throw new IllegalStateException("Only draft expenses can be modified or submitted");
    }
  }

  public void addAttachment(ExpenseAttachmentEntity attachment) {
    attachments.add(attachment);
    attachment.setExpense(this);
  }
}
