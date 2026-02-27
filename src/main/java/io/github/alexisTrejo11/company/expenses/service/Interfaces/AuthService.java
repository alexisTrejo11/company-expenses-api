package io.github.alexisTrejo11.company.expenses.service.Interfaces;

import io.github.alexisTrejo11.company.expenses.dto.Auth.LoginDTO;
import io.github.alexisTrejo11.company.expenses.dto.User.UserDTO;
import io.github.alexisTrejo11.company.expenses.dto.Auth.UserInsertDTO;
import io.github.alexisTrejo11.company.expenses.shared.Result;

public interface AuthService {
    Result<Void> validateRegisterCredentials(UserInsertDTO userInsertDTO);
    String ProcessRegister(UserDTO userDTO);

    Result<UserDTO> validateLoginCredentials(LoginDTO loginDTO);
    String processLogin(UserDTO userDTO);
}
