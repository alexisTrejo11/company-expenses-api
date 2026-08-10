package io.github.alexisTrejo11.construction.company.modules.budgeting.infrastructure.persistence.entity;

import io.github.alexisTrejo11.construction.company.modules.project.domain.model.ProjectEntity;
import io.github.alexisTrejo11.construction.company.shared.jpa.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


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
}