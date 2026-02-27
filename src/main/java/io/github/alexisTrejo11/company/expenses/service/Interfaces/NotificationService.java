package io.github.alexisTrejo11.company.expenses.service.Interfaces;

import io.github.alexisTrejo11.company.expenses.dto.Expenses.ExpenseDTO;
import io.github.alexisTrejo11.company.expenses.dto.Notification.NotificationDTO;
import io.github.alexisTrejo11.company.expenses.dto.Notification.NotificationInsertDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {

    Page<NotificationDTO> getNotificationByUserId(Long userId, Pageable pageable);
    NotificationDTO getNotificationById(Long notificationId);

    void createNotification(NotificationInsertDTO notificationInsertDTO);
    void sendNotificationFromExpense(ExpenseDTO expenseDTO);

    void markNotificationAsRead(Long notificationId);
}
