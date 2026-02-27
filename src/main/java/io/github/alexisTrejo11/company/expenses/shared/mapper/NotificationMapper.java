package io.github.alexisTrejo11.company.expenses.shared.mapper;

import io.github.alexisTrejo11.company.expenses.shared.dto.notification.NotificationDTO;
import io.github.alexisTrejo11.company.expenses.shared.dto.notification.NotificationInsertDTO;
import io.github.alexisTrejo11.company.expenses.model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "message", source = "message")
    Notification insertDtoToEntity(NotificationInsertDTO expenseInsertDTO);

    @Mapping(target = "userId", source = "user.id")
    NotificationDTO entityToDTO(Notification notification);

}
