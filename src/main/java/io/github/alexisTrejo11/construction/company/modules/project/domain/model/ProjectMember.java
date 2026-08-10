package io.github.alexisTrejo11.construction.company.modules.project.domain.model;

import io.github.alexisTrejo11.construction.company.modules.project.domain.ProjectRole;
import io.github.alexisTrejo11.construction.company.modules.user.model.User;
import io.github.alexisTrejo11.construction.company.shared.jpa.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "project_members",
    uniqueConstraints = @UniqueConstraint(columnNames = {"project_id", "user_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMember extends BaseModel {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id", nullable = false)
  private ProjectEntity project;


  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 30)
  private ProjectRole role;

  @Column(name = "assigned_at", nullable = false, updatable = false)
  private LocalDateTime assignedAt;
}
