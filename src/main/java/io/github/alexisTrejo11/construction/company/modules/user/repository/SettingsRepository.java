package io.github.alexisTrejo11.construction.company.modules.user.repository;

import io.github.alexisTrejo11.construction.company.modules.user.model.AdminSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingsRepository extends JpaRepository<AdminSettings, Long> {
}
