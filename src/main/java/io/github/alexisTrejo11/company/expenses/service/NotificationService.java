package io.github.alexisTrejo11.company.expenses.service;

import io.github.alexisTrejo11.company.expenses.model.Notification;
import io.github.alexisTrejo11.company.expenses.model.User;
import io.github.alexisTrejo11.company.expenses.repository.NotificationRepository;
import io.github.alexisTrejo11.company.expenses.repository.UserRepository;
import io.github.alexisTrejo11.company.expenses.shared.MessageGenerator;
import io.github.alexisTrejo11.company.expenses.shared.dto.expenses.ExpenseDTO;
import io.github.alexisTrejo11.company.expenses.shared.dto.notification.NotificationDTO;
import io.github.alexisTrejo11.company.expenses.shared.dto.notification.NotificationInsertDTO;
import io.github.alexisTrejo11.company.expenses.shared.enums.ExpenseStatus;
import io.github.alexisTrejo11.company.expenses.shared.enums.NotificationType;
import io.github.alexisTrejo11.company.expenses.shared.mapper.NotificationMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

  private final NotificationRepository notificationRepository;
  private final EmailService emailService;
  private final UserRepository userRepository;
  private final NotificationMapper notificationMapper;
  private final MessageGenerator message;

  public Page<NotificationDTO> getNotificationByUserId(Long userId, Pageable pageable) {
    Page<Notification> notificationPage = notificationRepository.findByUser_Id(userId, pageable);

    return notificationPage.map(notificationMapper::entityToDTO);
  }

  public NotificationDTO getNotificationById(Long notificationId) {
    Optional<Notification> optionalNotification = notificationRepository.findById(notificationId);

    return optionalNotification
        .map(notificationMapper::entityToDTO)
        .orElse(null);
  }


  @Transactional
  public void createNotification(NotificationInsertDTO notificationInsertDTO) {
    User user = userRepository.findById(notificationInsertDTO.getUserId())
        .orElseThrow(() -> new EntityNotFoundException("user Not Found"));

    Notification notification = notificationMapper.insertDtoToEntity(notificationInsertDTO);
    notification.setUser(user);

    notificationRepository.save(notification);
  }

  @Transactional
  @Async("taskExecutor")
  public void sendNotificationFromExpense(ExpenseDTO expenseDTO) {
    Notification notification = createNotificationFromExpense(expenseDTO);
    log.info("notification Successfully created with Id {} for user Id {}", notification.getId(), notification.getUser().getId());

    emailService.sendEmailFromNotification(notification);
    log.info("Email Successfully Send It To The Email From user Id {}", notification.getUser().getId());
  }

  public void markNotificationAsRead(Long id) {
    Notification notification = notificationRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(message.notFoundPlain("notification", "ID", id)));

    notification.setAsRead();
    notificationRepository.save(notification);
  }


  @Transactional
  private Notification createNotificationFromExpense(ExpenseDTO expenseDTO) {
    NotificationType notificationType = mapExpenseStatusToNotificationType(expenseDTO.getStatus());
    String notificationMessage = generateNotificationMessage(expenseDTO);

    User user = userRepository.findById(expenseDTO.getUserId())
        .orElseThrow(() -> new EntityNotFoundException("user not found"));


    Notification notification = Notification.builder()
        .createdAt(LocalDateTime.now())
        .type(notificationType)
        .read(Boolean.FALSE)
        .user(user)
        .message(notificationMessage)
        .build();

    notificationRepository.saveAndFlush(notification);

    return notification;
  }

  private NotificationType mapExpenseStatusToNotificationType(ExpenseStatus status) {
    return switch (status) {
      case PENDING -> NotificationType.EXPENSE_REJECTION;
      case APPROVED -> NotificationType.EXPENSE_APPROVAL;
      case REIMBURSED -> NotificationType.REIMBURSEMENT_COMPLETED;
      default -> throw new IllegalArgumentException("Unexpected status: " + status);
    };
  }

  private String generateNotificationMessage(ExpenseDTO expenseDTO) {
    StringBuilder notificationMessage = new StringBuilder();
    ExpenseStatus status = expenseDTO.getStatus();

    notificationMessage.append("Your Expense Requested At ").append(expenseDTO.getDate());

    switch (status) {
      case PENDING ->
          notificationMessage.append(" Was Successfully Processed and Will Be Checked For Validation As Soon As Possible.");
      case APPROVED -> notificationMessage.append(" Was Approved.");
      case REIMBURSED -> notificationMessage.append(" Was Reimbursed.");
      case REJECTED -> notificationMessage.append(" Was Rejected. Check The Expense For More Details.");
    }

    return notificationMessage.toString();
  }

}
