package com.formflow.meteo.app.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.formflow.meteo.app.domain.service.AuthService;
import com.formflow.meteo.app.domain.service.DocumentService;
import com.formflow.meteo.app.infra.entity.DocumentEntity;
import com.formflow.meteo.app.infra.entity.EmployeeEntity;

@Controller
@RequestMapping("/employee")
@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
public class EmployeeController {

    @Autowired
    private AuthService authService;

    @Autowired
    private DocumentService documentService;

    /**
     * 従業員ダッシュボード
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        EmployeeEntity currentUser = authService.getCurrentUser();
        
        // 個人の申請統計
        List<DocumentEntity> myApplications = documentService.findByEmployeeId(currentUser.getIdEmployee());
        long pendingCount = myApplications.stream().filter(app -> "申請中".equals(app.getStatus())).count();
        long approvedCount = myApplications.stream().filter(app -> "承認".equals(app.getStatus())).count();
        
        model.addAttribute("user", currentUser);
        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("approvedCount", approvedCount);
        model.addAttribute("totalApplications", myApplications.size());
        
        return "employee/dashboard";
    }

    /**
     * 交通費申請書入力画面
     */
    @GetMapping("/travel-expense/new")
    public String newTravelExpense(Model model) {
        EmployeeEntity currentUser = authService.getCurrentUser();
        model.addAttribute("user", currentUser);
        model.addAttribute("travelExpense", new DocumentEntity());
        return "employee/travel-expense-form";
    }

    /**
     * 交通費申請書保存
     */
    @PostMapping("/travel-expense")
    public String createTravelExpense(@ModelAttribute DocumentEntity travelExpense,
                                    RedirectAttributes redirectAttributes) {
        try {
            EmployeeEntity currentUser = authService.getCurrentUser();
            travelExpense.setEmployeeId(currentUser.getIdEmployee());
            travelExpense.setStatus("申請中");
            
            documentService.save(travelExpense);
            redirectAttributes.addFlashAttribute("successMessage", "交通費申請書を提出しました。");
            return "redirect:/employee/applications";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "申請書の提出に失敗しました: " + e.getMessage());
            return "redirect:/employee/travel-expense/new";
        }
    }

    /**
     * 有給申請書入力画面
     */
    @GetMapping("/paid-leave/new")
    public String newPaidLeave(Model model) {
        EmployeeEntity currentUser = authService.getCurrentUser();
        model.addAttribute("user", currentUser);
        model.addAttribute("paidLeave", new DocumentEntity());
        return "employee/paid-leave-form";
    }

    /**
     * 有給申請書保存
     */
    @PostMapping("/paid-leave")
    public String createPaidLeave(@ModelAttribute DocumentEntity paidLeave,
                                RedirectAttributes redirectAttributes) {
        try {
            EmployeeEntity currentUser = authService.getCurrentUser();
            paidLeave.setEmployeeId(currentUser.getIdEmployee());
            paidLeave.setStatus("申請中");
            
            documentService.save(paidLeave);
            redirectAttributes.addFlashAttribute("successMessage", "有給申請書を提出しました。");
            return "redirect:/employee/applications";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "申請書の提出に失敗しました: " + e.getMessage());
            return "redirect:/employee/paid-leave/new";
        }
    }

    /**
     * 提出済み申請一覧表示
     */
    @GetMapping("/applications")
    public String myApplications(Model model) {
        EmployeeEntity currentUser = authService.getCurrentUser();
        List<DocumentEntity> applications = documentService.findByEmployeeId(currentUser.getIdEmployee());
        
        model.addAttribute("user", currentUser);
        model.addAttribute("applications", applications);
        
        return "employee/applications";
    }

    /**
     * 申請詳細表示
     */
    @GetMapping("/applications/{id}")
    public String applicationDetail(@PathVariable Integer id, Model model) {
        EmployeeEntity currentUser = authService.getCurrentUser();
        DocumentEntity application = documentService.findById(id);
        
        // 自分の申請書かチェック
        if (application == null || !application.getEmployeeId().equals(currentUser.getIdEmployee())) {
            return "redirect:/employee/applications?error=not_found";
        }
        
        model.addAttribute("user", currentUser);
        model.addAttribute("application", application);
        
        return "employee/application-detail";
    }

    /**
     * 承認待ち申請一覧
     */
    @GetMapping("/applications/pending")
    public String pendingApplications(Model model) {
        EmployeeEntity currentUser = authService.getCurrentUser();
        List<DocumentEntity> applications = documentService.findByEmployeeId(currentUser.getIdEmployee());
        List<DocumentEntity> pendingApplications = applications.stream()
            .filter(app -> "申請中".equals(app.getStatus()))
            .toList();
        
        model.addAttribute("user", currentUser);
        model.addAttribute("applications", pendingApplications);
        
        return "employee/applications";
    }

    /**
     * 承認済み申請一覧
     */
    @GetMapping("/applications/approved")
    public String approvedApplications(Model model) {
        EmployeeEntity currentUser = authService.getCurrentUser();
        List<DocumentEntity> applications = documentService.findByEmployeeId(currentUser.getIdEmployee());
        List<DocumentEntity> approvedApplications = applications.stream()
            .filter(app -> "承認".equals(app.getStatus()))
            .toList();
        
        model.addAttribute("user", currentUser);
        model.addAttribute("applications", approvedApplications);
        
        return "employee/applications";
    }
} 