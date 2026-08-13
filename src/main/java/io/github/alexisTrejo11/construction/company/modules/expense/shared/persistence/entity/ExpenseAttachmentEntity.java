package io.github.alexisTrejo11.construction.company.modules.expense.shared.persistence.entity;

import io.github.alexisTrejo11.construction.company.shared.jpa.BaseModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "expense_attachments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseAttachmentEntity extends BaseModel {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "expense_id", nullable = false)
  private ExpenseEntity expense;

  @Column(name = "file_name", nullable = false, length = 255)
  private String fileName;

  @Column(name = "content_type", nullable = false, length = 100)
  private String contentType;

  @Column(nullable = false, length = 500)
  private String url;
}
