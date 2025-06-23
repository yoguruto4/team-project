package com.formflow.meteo.app.presentation.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/documents")
public class AdminDocumentController {

  @GetMapping
  public void displayAdminDocuments() {
    // 管理者用の申請書一覧画面を表示
  }

  @PostMapping("/travel-expense")
  public void approveOrRejectTravelExpense() {
    // 管理者による交通費申請の承認／却下処理
  }

  @PostMapping("/paid-leave")
  public void approveOrRejectPaidLeave() {
    // 管理者による有給申請の承認／却下処理
  }

  @PutMapping("/{id}/status")
  public void updateApprovalStatus(@PathVariable String id) {
    // 承認ステータス（承認／却下）を更新
  }
}
