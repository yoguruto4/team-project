package com.formflow.meteo.app.presentation.form;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.formflow.meteo.app.presentation.form.auth.LoginForm;

import org.junit.jupiter.api.DisplayName;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.Set;

public class LoginFormTest {

  private Validator createValidator() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    return factory.getValidator();
  }

  @Test
  @DisplayName("LoginFormのgetter/setterが正しく動作する")
  void testLoginForm() {
    LoginForm form = new LoginForm();
    form.setMail("test@example.com");
    form.setPassword("password");

    assertEquals("test@example.com", form.getMail());
    assertEquals("password", form.getPassword());
  }

  @Test
  @DisplayName("バリデーション: 正常なメール・パスワードでエラーなし")
  void testValidForm() {
    LoginForm form = new LoginForm();
    form.setMail("valid@example.com");
    form.setPassword("validpass");
    Validator validator = createValidator();
    Set<ConstraintViolation<LoginForm>> violations = validator.validate(form);
    assertEquals(0, violations.size());
  }

  @Test
  @DisplayName("バリデーション: メールが空の場合エラー")
  void testMailIsBlank() {
    LoginForm form = new LoginForm();
    form.setMail("");
    form.setPassword("password");
    Validator validator = createValidator();
    Set<ConstraintViolation<LoginForm>> violations = validator.validate(form);
    assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("login.mail.required")));
  }

  @Test
  @DisplayName("バリデーション: メール形式が不正な場合エラー")
  void testMailIsInvalid() {
    LoginForm form = new LoginForm();
    form.setMail("invalid-mail");
    form.setPassword("password");
    Validator validator = createValidator();
    Set<ConstraintViolation<LoginForm>> violations = validator.validate(form);
    assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("login.mail.invalid")));
  }

  @Test
  @DisplayName("バリデーション: パスワードが空の場合エラー")
  void testPasswordIsBlank() {
    LoginForm form = new LoginForm();
    form.setMail("test@example.com");
    form.setPassword("");
    Validator validator = createValidator();
    Set<ConstraintViolation<LoginForm>> violations = validator.validate(form);
    assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("login.password.required")));
  }

  @Test
  @DisplayName("バリデーション: メール・パスワード両方空の場合エラー")
  void testAllBlank() {
    LoginForm form = new LoginForm();
    form.setMail("");
    form.setPassword("");
    Validator validator = createValidator();
    Set<ConstraintViolation<LoginForm>> violations = validator.validate(form);
    assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("login.mail.required")));
    assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("login.password.required")));
  }
}
