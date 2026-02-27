package io.github.alexisTrejo11.company.expenses.service.Implementations;

import io.github.alexisTrejo11.company.expenses.dto.Dashboard.AdminDashboardDTO;
import io.github.alexisTrejo11.company.expenses.dto.Dashboard.DashboardStatsDTO;
import io.github.alexisTrejo11.company.expenses.dto.Settings.SettingsDTO;
import io.github.alexisTrejo11.company.expenses.model.AdminSettings;
import io.github.alexisTrejo11.company.expenses.repository.ExpenseRepository;
import io.github.alexisTrejo11.company.expenses.repository.SettingsRepository;
import io.github.alexisTrejo11.company.expenses.service.Interfaces.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final SettingsRepository settingsRepository;
    private final ExpenseRepository expenseRepository;

    @Override
    @Cacheable("adminDashboardCache")
    public AdminDashboardDTO getAdminDashboard() {
        DashboardStatsDTO statsDTO = getDashboardStats();
        int pendingReimbursements = expenseRepository.countPendingReimbursement();

        return AdminDashboardDTO.builder()
                .totalExpenses(statsDTO.getTotalExpenses())
                .pendingExpenses(statsDTO.getPendingExpenses())
                .totalRejectedExpenses(statsDTO.getTotalRejectedExpenses())
                .totalApprovedExpenses(statsDTO.getTotalApprovedExpenses())
                .totalReimbursementExpenses(statsDTO.getTotalReimbursementExpenses())
                .pendingReimbursements(pendingReimbursements)
                .build();
    }

    @Override
    @Transactional
    public void updateSettings(SettingsDTO settingsDTO) {
        List<AdminSettings> adminSettings = settingsRepository.findAll();

        if (adminSettings.isEmpty()) {
            AdminSettings createSettings = new AdminSettings(settingsDTO.getMaxExpenseLimit(), settingsDTO.getAllowedCategories());
            settingsRepository.saveAndFlush(createSettings);
        } else {
            AdminSettings currentSettings  = adminSettings.get(adminSettings.size() + 1);

            currentSettings.setMaxExpenseLimit(settingsDTO.getMaxExpenseLimit());
            currentSettings.setAllowedCategories(settingsDTO.getAllowedCategories());

            settingsRepository.saveAndFlush(currentSettings);
        }
    }

    @Override
    @Cacheable("adminSettingsCache")
    public SettingsDTO getCurrentSettings() {
        List<AdminSettings> adminSettings = settingsRepository.findAll();
        AdminSettings currentSettings  = adminSettings.get(0);

        return new SettingsDTO(currentSettings.getMaxExpenseLimit(), currentSettings.getAllowedCategories());
    }

    private DashboardStatsDTO getDashboardStats() {
        List<Object[]> results = expenseRepository.getDashboardStatsRaw();
        if (results.isEmpty()) {
            return new DashboardStatsDTO(0, 0, 0, 0, 0); // Handle empty case
        }
        Object[] row = results.get(0);
        return new DashboardStatsDTO(
                ((Number) row[0]).intValue(),
                ((Number) row[1]).intValue(),
                ((Number) row[2]).intValue(),
                ((Number) row[3]).intValue(),
                ((Number) row[4]).intValue()
        );
    }
}
