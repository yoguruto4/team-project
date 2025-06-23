package com.formflow.meteo.app.presentation.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

  @GetMapping
  public void displayDashboard() {
    // ログインユーザーの権限に応じてダッシュボード画面を表示
  }
}
