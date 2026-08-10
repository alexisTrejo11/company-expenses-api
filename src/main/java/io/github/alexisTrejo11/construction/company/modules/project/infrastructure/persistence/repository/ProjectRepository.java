package io.github.alexisTrejo11.construction.company.modules.project.infrastructure.persistence.repository;

import io.github.alexisTrejo11.construction.company.modules.project.domain.ProjectStatus;
import io.github.alexisTrejo11.construction.company.modules.project.domain.model.ProjectEntity;
import io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.response.ProjectsGlobalSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

  @Query("""
        SELECT p FROM ProjectEntity p
        WHERE p.deletedAt IS NULL
          AND (:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))
               OR LOWER(p.code) LIKE LOWER(CONCAT('%', :search, '%')))
          AND (:status IS NULL OR p.status = :status)
          AND (:city IS NULL OR LOWER(p.location.city) LIKE LOWER(CONCAT('%', :city, '%')))
    """)
  Page<ProjectEntity> findWithFilters(
      @Param("search") String search,
      @Param("status") ProjectStatus status,
      @Param("city") String city,
      Pageable pageable
  );

  @Query("""
        SELECT DISTINCT p FROM ProjectEntity p
        JOIN p.members m
        WHERE p.deletedAt IS NULL
          AND m.user.id = :userId
    """)
  Page<ProjectEntity> findByUserIdWithMembers(@Param("userId") Long userId, Pageable pageable);

  @Query("""
        SELECT new io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.response.ProjectsGlobalSummaryResponse(
            COUNT(p),
            COALESCE(SUM(CASE WHEN p.status = io.github.alexisTrejo11.construction.company.modules.project.domain.ProjectStatus.PLANNING THEN 1L ELSE 0L END), 0L),
            COALESCE(SUM(CASE WHEN p.status = io.github.alexisTrejo11.construction.company.modules.project.domain.ProjectStatus.IN_PROGRESS THEN 1L ELSE 0L END), 0L),
            COALESCE(SUM(CASE WHEN p.status = io.github.alexisTrejo11.construction.company.modules.project.domain.ProjectStatus.ON_HOLD THEN 1L ELSE 0L END), 0L),
            COALESCE(SUM(CASE WHEN p.status = io.github.alexisTrejo11.construction.company.modules.project.domain.ProjectStatus.COMPLETED THEN 1L ELSE 0L END), 0L),
            COALESCE(SUM(CASE WHEN p.status = io.github.alexisTrejo11.construction.company.modules.project.domain.ProjectStatus.CANCELLED THEN 1L ELSE 0L END), 0L),
            COALESCE(SUM(p.totalBudget), 0)
        )
        FROM ProjectEntity p
        WHERE p.deletedAt IS NULL
    """)
  ProjectsGlobalSummaryResponse getGlobalSummary();

  Optional<ProjectEntity> findByCode(String code);

  boolean existsByCode(String code);
}
