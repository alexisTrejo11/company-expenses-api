package io.github.alexisTrejo11.company.expenses.mapper;

import io.github.alexisTrejo11.company.expenses.dto.User.ProfileDTO;
import io.github.alexisTrejo11.company.expenses.dto.User.UserDTO;
import io.github.alexisTrejo11.company.expenses.dto.Auth.UserInsertDTO;
import io.github.alexisTrejo11.company.expenses.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "lastLogin", expression = "java(java.time.LocalDateTime.now())")
    User insertDtoToEntity(UserInsertDTO userInsertDTO);

    UserDTO entityToDTO(User user);
    ProfileDTO entityToProfileDTO(User user);



    void updateUser(@MappingTarget User user, UserInsertDTO userInsertDTO);
}
