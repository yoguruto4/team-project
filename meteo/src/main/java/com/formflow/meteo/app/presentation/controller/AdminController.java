package com.formflow.meteo.app.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.formflow.meteo.app.domain.service.AuthService;
import com.formflow.meteo.app.domain.service.DocumentService;
import com.formflow.meteo.app.infra.entity.DocumentEntity;
import com.formflow.meteo.app.infra.entity.EmployeeEntity;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private AuthService authService;

    @Autowired
    private DocumentService documentService;

    /**
     * 管理者ダッシュボード
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        EmployeeEntity currentUser = authService.getCurrentUser();
        model.addAttribute("user", currentUser);
        return "admin/dashboard";
    }

    /**
     * 申請一覧表示画面（全従業員の申請）
     */
    @GetMapping("/applications")
    public String applications(Model model,
                             @RequestParam(required = false) String status,
                             @RequestParam(required = false) String type) {
        
        EmployeeEntity currentUser = authService.getCurrentUser();
        List<DocumentEntity> applications;
        
        // フィルタリングロジック
        if (status != null && !status.isEmpty()) {
            applications = documentService.findByStatus(status);
        } else if (type != null && !type.isEmpty()) {
            applications = documentService.findByType(type);
        } else {
            applications = documentService.findAllApplications();
        }
        
        model.addAttribute("user", currentUser);
        model.addAttribute("applications", applications);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("selectedType", type);
        
        return "admin/applications";
    }

    /**
     * 申請詳細表示画面
     */
    @GetMapping("/applications/{id}")
    public String applicationDetail(@PathVariable Integer id, Model model) {
        EmployeeEntity currentUser = authService.getCurrentUser();
        DocumentEntity application = documentService.findById(id);
        
        if (application == null) {
            return "redirect:/admin/applications?error=not_found";
        }
        
        model.addAttribute("user", currentUser);
        model.addAttribute("application", application);
        
        return "admin/application-detail";
    }

    /**
     * 申請承認・却下処理
     */
    @PostMapping("/applications/{id}/approve")
    public String approveApplication(@PathVariable Integer id,
                                   @RequestParam String action,
                                   @RequestParam(required = false) String comment,
                                   RedirectAttributes redirectAttributes) {
        
        EmployeeEntity currentUser = authService.getCurrentUser();
        
        try {
            if ("approve".equals(action)) {
                documentService.approveApplication(id, currentUser.getIdEmployee(), comment);
                redirectAttributes.addFlashAttribute("successMessage", "申請を承認しました。");
            } else if ("reject".equals(action)) {
                documentService.rejectApplication(id, currentUser.getIdEmployee(), comment);
                redirectAttributes.addFlashAttribute("successMessage", "申請を却下しました。");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "処理中にエラーが発生しました: " + e.getMessage());
        }
        
        return "redirect:/admin/applications/" + id;
    }

    /**
     * 申請履歴管理画面
     */
    @GetMapping("/history")
    public String applicationHistory(Model model) {
        EmployeeEntity currentUser = authService.getCurrentUser();
        List<DocumentEntity> completedApplications = documentService.findCompletedApplications();
        
        model.addAttribute("user", currentUser);
        model.addAttribute("applications", completedApplications);
        
        return "admin/history";
    }

    /**
     * システム設定画面
     */
    @GetMapping("/settings")
    public String settings(Model model) {
        EmployeeEntity currentUser = authService.getCurrentUser();
        model.addAttribute("user", currentUser);
        return "admin/settings";
    }
} 