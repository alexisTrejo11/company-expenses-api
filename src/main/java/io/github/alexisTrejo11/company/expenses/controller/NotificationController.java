package io.github.alexisTrejo11.company.expenses.controller;

import io.github.alexisTrejo11.company.expenses.service.NotificationService;
import io.github.alexisTrejo11.company.expenses.shared.MessageGenerator;
import io.github.alexisTrejo11.company.expenses.shared.ResponseWrapper;
import io.github.alexisTrejo11.company.expenses.shared.dto.notification.NotificationDTO;
import io.github.alexisTrejo11.company.expenses.shared.dto.notification.NotificationInsertDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

  private final NotificationService notificationService;
  private final MessageGenerator message;

  @Operation(summary = "Get notification by ID", description = "Retrieve a notification by its unique ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "notification successfully fetched."),
      @ApiResponse(responseCode = "404", description = "notification not found.")
  })
  @GetMapping("/{notificationId}")
  public ResponseEntity<ResponseWrapper<NotificationDTO>> getNotificationById(@PathVariable Long id) {
    NotificationDTO notification = notificationService.getNotificationById(id);
    if (notification == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseWrapper.notFound("notification", "ID", id));
    }

    return ResponseEntity.ok(ResponseWrapper.found(notification, "notification", "ID", id));
  }

  @Operation(summary = "Get Notifications by user ID", description = "Retrieve all notifications for a specific user.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Notifications successfully fetched."),
      @ApiResponse(responseCode = "404", description = "No notifications found for the user.")
  })
  @GetMapping("by-user/{userId}")
  public ResponseEntity<ResponseWrapper<Page<NotificationDTO>>> getNotificationByUserId(@PathVariable Long userId,
                                                                                        @RequestParam(defaultValue = "0") int page,
                                                                                        @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<NotificationDTO> notifications = notificationService.getNotificationByUserId(userId, pageable);

    return ResponseEntity.ok(ResponseWrapper.found(notifications, "Notifications", "userID", userId));
  }

  @Operation(summary = "Create notification", description = "Create a new notification.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "notification successfully created."),
      @ApiResponse(responseCode = "400", description = "Invalid input data.")
  })
  @PostMapping
  public ResponseEntity<ResponseWrapper<Void>> createNotification(@Valid @RequestBody NotificationInsertDTO notificationInsertDTO) {
    notificationService.createNotification(notificationInsertDTO);

    return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.created("notification"));
  }

  @Operation(summary = "Mark notification as Read", description = "Mark a notification as read by its ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "notification successfully marked as read."),
      @ApiResponse(responseCode = "404", description = "notification not found.")
  })
  @PutMapping("set-as-read/{notificationId}")
  public ResponseEntity<ResponseWrapper<NotificationDTO>> markNotificationAsRead(@PathVariable Long notificationId) {
    notificationService.markNotificationAsRead(notificationId);

    return ResponseEntity.ok(ResponseWrapper.success(message.successAction("notification", "mark as read")));
  }
}
