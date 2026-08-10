package io.github.alexisTrejo11.construction.company.modules.project.domain.model;

import io.github.alexisTrejo11.construction.company.shared.jpa.BaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "project_phases")
@Getter
@Setter
public class ProjectPhase extends BaseModel {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id", nullable = false)
  private ProjectEntity project;

  @Column(nullable = false, length = 100)
  private String name; // e.g., "Foundations", "Structural Framing", "MEP Installations"

  @Column(name = "sequence_order", nullable = false)
  private Integer sequenceOrder;

  @Column(name = "allocated_budget", nullable = false, precision = 15, scale = 2)
  private BigDecimal allocatedBudget = BigDecimal.ZERO;

  @Column(name = "start_date")
  private LocalDate startDate;

  @Column(name = "end_date")
  private LocalDate endDate;

}
