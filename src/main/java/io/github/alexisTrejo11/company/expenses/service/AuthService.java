package io.github.alexisTrejo11.company.expenses.service;

import io.github.alexisTrejo11.company.expenses.shared.dto.user.UserDTO;
import io.github.alexisTrejo11.company.expenses.shared.dto.auth.LoginDTO;
import io.github.alexisTrejo11.company.expenses.shared.dto.auth.UserInsertDTO;
import io.github.alexisTrejo11.company.expenses.shared.mapper.UserMapper;
import io.github.alexisTrejo11.company.expenses.shared.PasswordHandler;
import io.github.alexisTrejo11.company.expenses.model.User;
import io.github.alexisTrejo11.company.expenses.repository.UserRepository;
import io.github.alexisTrejo11.company.expenses.shared.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final JWTService jwtService;

    public Result<Void> validateRegisterCredentials(UserInsertDTO userInsertDTO) {
        Optional<User> optionalUser = userRepository.findByEmail(userInsertDTO.getEmail());
        if (optionalUser.isPresent()) {
            return Result.error("Email Already Taken");
        }

        return Result.success();
    }

    @Transactional
    public String ProcessRegister(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new RuntimeException("Can't Process Login"));

        Consumer<Map<String, Object>> extraClaimsConsumer = extraClaims -> {
            extraClaims.put("role", user.getRole().name());
            extraClaims.put("userId", user.getId());
        };

        return jwtService.generateToken(user, extraClaimsConsumer);
    }

    public Result<UserDTO> validateLoginCredentials(LoginDTO loginDTO) {
        Optional<User> optionalUser = getUserByEmail(loginDTO.getEmail());
        if (optionalUser.isEmpty()) {
            return Result.error("user With Given Credentials Not Found");
        }

        User user = optionalUser.get();

        boolean isPasswordCorrect = PasswordHandler.validatePassword(loginDTO.getPassword(), user.getPassword());
        if (!isPasswordCorrect) {
            return Result.error("Wrong Password");
        }

        return Result.success(userMapper.entityToDTO(user));
    }

    @Transactional
    public String processLogin(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new RuntimeException("Can't Process Login"));

        Consumer<Map<String, Object>> extraClaimsConsumer = extraClaims -> {
            extraClaims.put("role", user.getRole().name());
            extraClaims.put("userId", user.getId());
        };

        String jwt = jwtService.generateToken(user, extraClaimsConsumer);

        user.updateLastLogin();
        userRepository.saveAndFlush(user);

        return jwt;
    }


    @Cacheable(value = "userCredentialsCache", key = "#email")
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
