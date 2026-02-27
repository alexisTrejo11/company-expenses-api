package io.github.alexisTrejo11.company.expenses.mapper;

import io.github.alexisTrejo11.company.expenses.dto.Notification.NotificationDTO;
import io.github.alexisTrejo11.company.expenses.dto.Notification.NotificationInsertDTO;
import io.github.alexisTrejo11.company.expenses.model.Notification;
import io.github.alexisTrejo11.company.expenses.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-21T18:43:16-0600",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.jar, environment: Java 17.0.12 (JetBrains s.r.o.)"
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
        notificationDTO.setType( notification.getType() );
        notificationDTO.setMessage( notification.getMessage() );
        notificationDTO.setRead( notification.getRead() );

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
