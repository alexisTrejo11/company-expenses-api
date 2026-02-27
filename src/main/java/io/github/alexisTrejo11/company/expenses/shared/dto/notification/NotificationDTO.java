package io.github.alexisTrejo11.company.expenses.shared.dto.notification;

import io.github.alexisTrejo11.company.expenses.shared.enums.NotificationType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotificationDTO {
    private Long id;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private String message;

    private Boolean read;
}
