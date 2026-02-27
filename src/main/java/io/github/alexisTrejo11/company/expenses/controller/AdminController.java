package io.github.alexisTrejo11.company.expenses.controller;

import io.github.alexisTrejo11.company.expenses.service.AdminService;
import io.github.alexisTrejo11.company.expenses.shared.ResponseWrapper;
import io.github.alexisTrejo11.company.expenses.shared.dto.dashboard.AdminDashboardDTO;
import io.github.alexisTrejo11.company.expenses.shared.dto.settings.SettingsDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/admin")
@RequiredArgsConstructor
public class AdminController {

  private final AdminService adminService;

  @GetMapping("/dashboard")
  public ResponseEntity<AdminDashboardDTO> getAdminDashboard() {
    AdminDashboardDTO adminDashboard = adminService.getAdminDashboard();

    return ResponseEntity.ok(adminDashboard);
  }


  @PutMapping("/settings")
  public ResponseEntity<ResponseWrapper<String>> updateSettings(@Valid @RequestBody SettingsDTO settingsDTO) {
    adminService.updateSettings(settingsDTO);

    return ResponseEntity.ok(ResponseWrapper.success(null, "settings updated successfully"));
  }

  @GetMapping("/settings")
  public ResponseWrapper<SettingsDTO> getCurrentSettings() {
    SettingsDTO settingsDTO = adminService.getCurrentSettings();
    return ResponseWrapper.success(settingsDTO, "Current settings Successfully Fetched");
  }

}

