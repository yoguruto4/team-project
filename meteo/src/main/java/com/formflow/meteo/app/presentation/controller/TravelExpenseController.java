package com.formflow.meteo.app.presentation.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/form/travel-expense")
public class TravelExpenseController {

  @GetMapping
  public void displayTravelExpenseForm() {
    // 交通費申請フォームを表示
  }

  @PostMapping
  public void submitTravelExpense() {
    // 交通費申請内容をデータベースに登録
  }
}
