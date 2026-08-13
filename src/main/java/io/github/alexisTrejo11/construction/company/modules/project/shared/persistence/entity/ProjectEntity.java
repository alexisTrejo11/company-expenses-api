package io.github.alexisTrejo11.construction.company.modules.project.shared.persistence.entity;

import io.github.alexisTrejo11.construction.company.modules.project.shared.domain.ProjectStatus;
import io.github.alexisTrejo11.construction.company.modules.project.shared.domain.SiteLocation;
import io.github.alexisTrejo11.construction.company.shared.Result;
import io.github.alexisTrejo11.construction.company.shared.exception.BusinessRuleException;
import io.github.alexisTrejo11.construction.company.shared.jpa.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectEntity extends BaseModel {

  @Column(nullable = false, length = 150)
  private String name;

  @Column(unique = true, nullable = false, length = 30)
  private String code;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private ProjectStatus status = ProjectStatus.PLANNING;

  @Column(name = "total_budget", nullable = false, precision = 15, scale = 2)
  private BigDecimal totalBudget = BigDecimal.ZERO;

  @Embedded
  private SiteLocation location;

  @Column(name = "start_date")
  private LocalDate startDate;

  @Column(name = "estimated_end_date")
  private LocalDate estimatedEndDate;

  @Column(name = "actual_end_date")
  private LocalDate actualEndDate;

  @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ProjectPhase> phases = new ArrayList<>();

  @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ProjectMember> members = new ArrayList<>();

  public void addPhase(ProjectPhase phase) {
    phases.add(phase);
    phase.setProject(this);
  }

  public void removePhase(ProjectPhase phase) {
    phases.remove(phase);
    phase.setProject(null);
  }

  public void addMember(ProjectMember member) {
    members.add(member);
    member.setProject(this);
  }

  public Result<Void> validate() {
    if (totalBudget.compareTo(BigDecimal.ZERO) < 0) {
      return Result.business("Total budget cannot be negative");
    }

    return Result.success();
  }

  public void updateStatus(ProjectStatus newStatus) {
    if (this.getActualEndDate() != null || this.status == ProjectStatus.COMPLETED) {
      throw new BusinessRuleException("Project has already been completed");
    }

    this.status = newStatus;
  }
}
