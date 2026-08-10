package io.github.alexisTrejo11.construction.company.modules.notification.repository;

import io.github.alexisTrejo11.construction.company.modules.notification.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Page<Notification> findByUser_Id(Long userId, Pageable pageable);
}
