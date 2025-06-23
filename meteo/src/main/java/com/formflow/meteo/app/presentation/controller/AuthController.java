package com.formflow.meteo.app.presentation.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AuthController {

  @PostMapping
  public void login() {
    // ユーザーのログイン処理を行い、ロールに応じたダッシュボードにリダイレクト
  }
}
