package io.github.alexisTrejo11.company.expenses.service.Implementations;

import io.github.alexisTrejo11.company.expenses.dto.User.ProfileDTO;
import io.github.alexisTrejo11.company.expenses.dto.User.UserDTO;
import io.github.alexisTrejo11.company.expenses.dto.Auth.UserInsertDTO;
import io.github.alexisTrejo11.company.expenses.mapper.UserMapper;
import io.github.alexisTrejo11.company.expenses.shared.PasswordHandler;
import io.github.alexisTrejo11.company.expenses.model.User;
import io.github.alexisTrejo11.company.expenses.shared.enums.Role;
import io.github.alexisTrejo11.company.expenses.repository.UserRepository;
import io.github.alexisTrejo11.company.expenses.service.Interfaces.UserService;
import io.github.alexisTrejo11.company.expenses.shared.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDTO createUser(UserInsertDTO userInsertDTO, Role role) {
        User user = userMapper.insertDtoToEntity(userInsertDTO);
        user.setRole(role);
        user.setPassword(PasswordHandler.hashPassword(userInsertDTO.getPassword()));

        userRepository.saveAndFlush(user);

        return userMapper.entityToDTO(user);
    }

    @Override
    public Result<UserDTO> getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser
                .map(user -> Result.success(userMapper.entityToDTO(user)))
                .orElseGet(() -> Result.error("User with id " + userId + " not found") );
    }

    @Override
    public Result<ProfileDTO> getProfileById(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser
                .map(user -> Result.success(userMapper.entityToProfileDTO(user)))
                .orElseGet(() -> Result.error("User with email [" + email + "] not found") );
    }

    @Override
    public Result<Void> updateUser(Long userId, UserInsertDTO userInsertDTO) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser
                .map(user -> {
                    userMapper.updateUser(user, userInsertDTO);
                    userRepository.saveAndFlush(user);
                    return Result.success();
                })
                .orElseGet(() -> Result.error("User with id " + userId + " not found"));
    }

    @Override
    public Result<Void> deleteUserById(Long userId) {
        if (!userRepository.existsById(userId)) {
            return Result.error("User with id " + userId + " not found");
        }

        userRepository.deleteById(userId);
        return Result.success();
    }
}
