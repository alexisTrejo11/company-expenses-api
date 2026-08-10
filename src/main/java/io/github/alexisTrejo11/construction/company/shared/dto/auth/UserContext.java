package io.github.alexisTrejo11.construction.company.shared.dto.auth;

import java.util.Set;

public record UserContext(
    Long userId,
    String email,
    Set<String> roles
) {
    public boolean hasRole(String role) {
        return roles != null && roles.contains(role);
    }
}