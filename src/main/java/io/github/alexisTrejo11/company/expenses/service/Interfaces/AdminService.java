package io.github.alexisTrejo11.company.expenses.service.Interfaces;

import io.github.alexisTrejo11.company.expenses.dto.Dashboard.AdminDashboardDTO;
import io.github.alexisTrejo11.company.expenses.dto.Settings.SettingsDTO;

public interface AdminService {
    AdminDashboardDTO getAdminDashboard();
    void updateSettings(SettingsDTO settingsDTO);
    SettingsDTO getCurrentSettings();
}
