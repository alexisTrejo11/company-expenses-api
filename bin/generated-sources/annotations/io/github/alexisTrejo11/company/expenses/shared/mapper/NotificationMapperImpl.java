package io.github.alexisTrejo11.company.expenses.shared.mapper;

import io.github.alexisTrejo11.company.expenses.model.Notification;
import io.github.alexisTrejo11.company.expenses.model.User;
import io.github.alexisTrejo11.company.expenses.shared.dto.notification.NotificationDTO;
import io.github.alexisTrejo11.company.expenses.shared.dto.notification.NotificationInsertDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-03T13:31:57-0600",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class NotificationMapperImpl implements NotificationMapper {

    @Override
    public Notification insertDtoToEntity(NotificationInsertDTO expenseInsertDTO) {
        if ( expenseInsertDTO == null ) {
            return null;
        }

        Notification.NotificationBuilder notification = Notification.builder();

        notification.message( expenseInsertDTO.getMessage() );
        notification.type( expenseInsertDTO.getType() );

        notification.createdAt( java.time.LocalDateTime.now() );

        return notification.build();
    }

    @Override
    public NotificationDTO entityToDTO(Notification notification) {
        if ( notification == null ) {
            return null;
        }

        NotificationDTO notificationDTO = new NotificationDTO();

        notificationDTO.setUserId( notificationUserId( notification ) );
        notificationDTO.setId( notification.getId() );
        notificationDTO.setMessage( notification.getMessage() );
        notificationDTO.setRead( notification.getRead() );
        notificationDTO.setType( notification.getType() );

        return notificationDTO;
    }

    private Long notificationUserId(Notification notification) {
        if ( notification == null ) {
            return null;
        }
        User user = notification.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
