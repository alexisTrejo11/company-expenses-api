package io.github.alexisTrejo11.company.expenses.service.Implementations;

import io.github.alexisTrejo11.company.expenses.dto.Auth.LoginDTO;
import io.github.alexisTrejo11.company.expenses.dto.User.UserDTO;
import io.github.alexisTrejo11.company.expenses.dto.Auth.UserInsertDTO;
import io.github.alexisTrejo11.company.expenses.mapper.UserMapper;
import io.github.alexisTrejo11.company.expenses.auth.JWTService;
import io.github.alexisTrejo11.company.expenses.shared.PasswordHandler;
import io.github.alexisTrejo11.company.expenses.model.User;
import io.github.alexisTrejo11.company.expenses.repository.UserRepository;
import io.github.alexisTrejo11.company.expenses.service.Interfaces.AuthService;
import io.github.alexisTrejo11.company.expenses.shared.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final JWTService jwtService;

    @Override
    public Result<Void> validateRegisterCredentials(UserInsertDTO userInsertDTO) {
        Optional<User> optionalUser = userRepository.findByEmail(userInsertDTO.getEmail());
        if (optionalUser.isPresent()) {
            return Result.error("Email Already Taken");
        }

        return Result.success();
    }

    @Override
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

    @Override
    public Result<UserDTO> validateLoginCredentials(LoginDTO loginDTO) {
        Optional<User> optionalUser = getUserByEmail(loginDTO.getEmail());
        if (optionalUser.isEmpty()) {
            return Result.error("User With Given Credentials Not Found");
        }

        User user = optionalUser.get();

        boolean isPasswordCorrect = PasswordHandler.validatePassword(loginDTO.getPassword(), user.getPassword());
        if (!isPasswordCorrect) {
            return Result.error("Wrong Password");
        }

        return Result.success(userMapper.entityToDTO(user));
    }

    @Override
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
