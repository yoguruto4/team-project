package com.formflow.meteo.app.presentation.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/form/template")
public class TravelExpenseTemplateController {

  @GetMapping
  public void displayTemplateForm() {
    // 交通費テンプレートの作成画面を表示
  }

  @PostMapping
  public void saveTemplate() {
    // テンプレート情報を保存（再利用用）
  }
}
