package io.github.alexisTrejo11.company.expenses.mapper;

import io.github.alexisTrejo11.company.expenses.dto.Notification.NotificationDTO;
import io.github.alexisTrejo11.company.expenses.dto.Notification.NotificationInsertDTO;
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
