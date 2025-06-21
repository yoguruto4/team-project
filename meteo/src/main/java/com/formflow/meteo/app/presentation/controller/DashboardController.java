package com.formflow.meteo.app.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.formflow.meteo.app.domain.service.AuthService;
import com.formflow.meteo.app.infra.entity.EmployeeEntity;
import com.formflow.meteo.app.infra.enums.UserRole;

@Controller
public class DashboardController {

    @Autowired
    private AuthService authService;

    /**
     * ダッシュボード画面
     * ロールに応じて表示内容を変更
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        EmployeeEntity currentUser = authService.getCurrentUser();
        UserRole userRole = authService.getCurrentUserRole();

        model.addAttribute("user", currentUser);
        model.addAttribute("userRole", userRole);
        model.addAttribute("isAdmin", authService.isCurrentUserAdmin());

        // ロールに応じて異なるダッシュボードを表示
        if (authService.isCurrentUserAdmin()) {
            return "admin/dashboard";
        } else {
            return "employee/dashboard";
        }
    }

    /**
     * 管理者専用ページ
     * 管理者のみアクセス可能
     */
    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard(Model model) {
        EmployeeEntity currentUser = authService.getCurrentUser();
        model.addAttribute("user", currentUser);
        return "admin/dashboard";
    }

    /**
     * 従業員ページ
     * 全ユーザーがアクセス可能
     */
    @GetMapping("/employee/dashboard")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public String employeeDashboard(Model model) {
        EmployeeEntity currentUser = authService.getCurrentUser();
        model.addAttribute("user", currentUser);
        return "employee/dashboard";
    }
} 