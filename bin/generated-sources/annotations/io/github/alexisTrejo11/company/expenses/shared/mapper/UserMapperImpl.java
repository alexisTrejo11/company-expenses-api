package io.github.alexisTrejo11.company.expenses.shared.mapper;

import io.github.alexisTrejo11.company.expenses.model.User;
import io.github.alexisTrejo11.company.expenses.shared.dto.auth.UserInsertDTO;
import io.github.alexisTrejo11.company.expenses.shared.dto.user.ProfileDTO;
import io.github.alexisTrejo11.company.expenses.shared.dto.user.UserDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-27T14:24:35-0600",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User insertDtoToEntity(UserInsertDTO userInsertDTO) {
        if ( userInsertDTO == null ) {
            return null;
        }

        User user = new User();

        user.setDepartment( userInsertDTO.getDepartment() );
        user.setEmail( userInsertDTO.getEmail() );
        user.setFirstName( userInsertDTO.getFirstName() );
        user.setLastName( userInsertDTO.getLastName() );
        user.setPassword( userInsertDTO.getPassword() );

        user.setCreatedAt( java.time.LocalDateTime.now() );
        user.setUpdatedAt( java.time.LocalDateTime.now() );
        user.setLastLogin( java.time.LocalDateTime.now() );

        return user;
    }

    @Override
    public UserDTO entityToDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setDepartment( user.getDepartment() );
        userDTO.setEmail( user.getEmail() );
        userDTO.setFirstName( user.getFirstName() );
        userDTO.setLastName( user.getLastName() );
        userDTO.setId( user.getId() );
        userDTO.setPassword( user.getPassword() );
        userDTO.setRole( user.getRole() );

        return userDTO;
    }

    @Override
    public ProfileDTO entityToProfileDTO(User user) {
        if ( user == null ) {
            return null;
        }

        ProfileDTO profileDTO = new ProfileDTO();

        profileDTO.setDepartment( user.getDepartment() );
        profileDTO.setEmail( user.getEmail() );
        profileDTO.setFirstName( user.getFirstName() );
        profileDTO.setLastName( user.getLastName() );

        return profileDTO;
    }

    @Override
    public void updateUser(User user, UserInsertDTO userInsertDTO) {
        if ( userInsertDTO == null ) {
            return;
        }

        user.setDepartment( userInsertDTO.getDepartment() );
        user.setEmail( userInsertDTO.getEmail() );
        user.setFirstName( userInsertDTO.getFirstName() );
        user.setLastName( userInsertDTO.getLastName() );
        user.setPassword( userInsertDTO.getPassword() );
    }
}
