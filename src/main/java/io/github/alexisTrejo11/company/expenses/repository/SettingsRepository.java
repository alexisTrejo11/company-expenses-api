package io.github.alexisTrejo11.company.expenses.repository;

import io.github.alexisTrejo11.company.expenses.model.AdminSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingsRepository extends JpaRepository<AdminSettings, Long> {
}
