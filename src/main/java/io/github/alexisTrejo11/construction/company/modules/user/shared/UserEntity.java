package io.github.alexisTrejo11.construction.company.modules.user.shared;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(nullable = false, unique = true, length = 150)
  private String email;

  @Column(nullable = false, length = 255)
  private String password;

  @Column(name = "full_name", nullable = false, length = 150)
  private String fullName;

  @Column(length = 20)
  private String phone;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 30)
  @Builder.Default
  private UserRole role = UserRole.UNKNOWN;

  @Column(name = "is_active", nullable = false)
  @Builder.Default
  private boolean active = true;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}