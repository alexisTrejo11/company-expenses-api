package io.github.alexisTrejo11.company.expenses.service.Interfaces;

import io.github.alexisTrejo11.company.expenses.dto.User.ProfileDTO;
import io.github.alexisTrejo11.company.expenses.dto.User.UserDTO;
import io.github.alexisTrejo11.company.expenses.dto.Auth.UserInsertDTO;
import io.github.alexisTrejo11.company.expenses.shared.enums.Role;
import io.github.alexisTrejo11.company.expenses.shared.Result;

public interface UserService {
    Result<UserDTO> getUserById(Long userId);
    Result<ProfileDTO> getProfileById(String email);

    UserDTO createUser(UserInsertDTO userInsertDTO, Role role);
    Result<Void> updateUser(Long userId, UserInsertDTO userInsertDTO);
    Result<Void> deleteUserById(Long userId);
}
