package io.github.alexisTrejo11.company.expenses.shared.enums;

import java.util.Arrays;
import java.util.Optional;

public enum ExpenseStatus {
    PENDING,
    APPROVED,
    REJECTED,
    REIMBURSED;

    public static Optional<ExpenseStatus> findStatus(String name) {
        return Arrays.stream(ExpenseStatus.values())
                .filter(status -> status.name().equalsIgnoreCase(name))
                .findFirst();
    }
}