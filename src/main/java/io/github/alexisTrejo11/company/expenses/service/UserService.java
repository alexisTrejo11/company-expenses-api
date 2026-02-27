package io.github.alexisTrejo11.company.expenses.service;

import io.github.alexisTrejo11.company.expenses.shared.dto.user.ProfileDTO;
import io.github.alexisTrejo11.company.expenses.shared.dto.user.UserDTO;
import io.github.alexisTrejo11.company.expenses.shared.dto.auth.UserInsertDTO;
import io.github.alexisTrejo11.company.expenses.shared.mapper.UserMapper;
import io.github.alexisTrejo11.company.expenses.shared.PasswordHandler;
import io.github.alexisTrejo11.company.expenses.model.User;
import io.github.alexisTrejo11.company.expenses.shared.enums.Role;
import io.github.alexisTrejo11.company.expenses.repository.UserRepository;
import io.github.alexisTrejo11.company.expenses.shared.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDTO createUser(UserInsertDTO userInsertDTO, Role role) {
        User user = userMapper.insertDtoToEntity(userInsertDTO);
        user.setRole(role);
        user.setPassword(PasswordHandler.hashPassword(userInsertDTO.getPassword()));

        userRepository.saveAndFlush(user);

        return userMapper.entityToDTO(user);
    }

    public Result<UserDTO> getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser
                .map(user -> Result.success(userMapper.entityToDTO(user)))
                .orElseGet(() -> Result.error("user with id " + userId + " not found") );
    }

    public Result<ProfileDTO> getProfileById(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser
                .map(user -> Result.success(userMapper.entityToProfileDTO(user)))
                .orElseGet(() -> Result.error("user with email [" + email + "] not found") );
    }

    public Result<Void> updateUser(Long userId, UserInsertDTO userInsertDTO) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser
                .map(user -> {
                    userMapper.updateUser(user, userInsertDTO);
                    userRepository.saveAndFlush(user);
                    return Result.success();
                })
                .orElseGet(() -> Result.error("user with id " + userId + " not found"));
    }

    public Result<Void> deleteUserById(Long userId) {
        if (!userRepository.existsById(userId)) {
            return Result.error("user with id " + userId + " not found");
        }

        userRepository.deleteById(userId);
        return Result.success();
    }
}
