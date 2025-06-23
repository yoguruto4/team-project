package com.formflow.meteo.app.presentation.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/form")
public class FormSelectionController {

  @GetMapping
  public void displayFormSelection() {
    // 申請書の種類を選択する画面を表示
  }
}
