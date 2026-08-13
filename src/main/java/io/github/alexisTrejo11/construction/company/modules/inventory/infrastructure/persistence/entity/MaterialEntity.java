package io.github.alexisTrejo11.construction.company.modules.inventory.infrastructure.persistence.entity;

import io.github.alexisTrejo11.construction.company.modules.project.shared.persistence.entity.ProjectPhase;
import io.github.alexisTrejo11.construction.company.shared.jpa.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "materials")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialEntity extends BaseModel {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 20, nullable = false)
    private String unit; // ej. "m3", "kg", "pz", "ton"

    @Column(name = "estimated_quantity", nullable = false, precision = 12, scale = 4)
    private BigDecimal estimatedQuantity;

    @Column(name = "used_quantity", nullable = false, precision = 12, scale = 4)
    @Builder.Default
    private BigDecimal usedQuantity = BigDecimal.ZERO;

    @Column(name = "unit_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phase_id", nullable = false)
    @ToString.Exclude
    private ProjectPhase phase;
}