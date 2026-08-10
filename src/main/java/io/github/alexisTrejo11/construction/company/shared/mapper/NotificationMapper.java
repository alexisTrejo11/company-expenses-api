package io.github.alexisTrejo11.construction.company.shared.mapper;

import io.github.alexisTrejo11.construction.company.shared.dto.notification.NotificationDTO;
import io.github.alexisTrejo11.construction.company.shared.dto.notification.NotificationInsertDTO;
import io.github.alexisTrejo11.construction.company.modules.notification.model.Notification;
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
