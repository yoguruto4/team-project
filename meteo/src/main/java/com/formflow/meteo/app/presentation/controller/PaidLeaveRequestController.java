package com.formflow.meteo.app.presentation.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/form/paid-leave")
public class PaidLeaveRequestController {

  @GetMapping
  public void displayPaidLeaveForm() {
    // 有給申請フォームを表示
  }

  @PostMapping
  public void submitPaidLeaveRequest() {
    // 有給申請内容をデータベースに登録
  }
}
