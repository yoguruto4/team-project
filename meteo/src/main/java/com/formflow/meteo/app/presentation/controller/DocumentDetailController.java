package com.formflow.meteo.app.presentation.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/document")
public class DocumentDetailController {

  @GetMapping("/{id}")
  public void displayDocumentDetail(@PathVariable String id) {
    // 申請 ID に応じて、申請の詳細情報を表示
  }

  @GetMapping("/travel-expense/{id}")
  public void displayTravelExpenseDetail(@PathVariable String id) {
    // 指定された交通費申請の詳細を表示
  }

  @GetMapping("/paid-leave/{id}")
  public void displayPaidLeaveDetail(@PathVariable String id) {
    // 指定された有給申請の詳細を表示
  }
}
