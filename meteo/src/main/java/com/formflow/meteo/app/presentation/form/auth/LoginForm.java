package com.formflow.meteo.app.presentation.form.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * ログイン画面用フォーム
 * 認証処理を行うためのフォームクラス
 * ユーザー名とパスワードを含む
 */
@Data
public class LoginForm {
  /** メールアドレス */
  @NotBlank(message = "login.mail.required")
  @Email(message = "login.mail.invalid")
  private String mail;

  /** パスワード */
  @NotBlank(message = "login.password.required")
  private String password;
}
