package com.formflow.meteo.app.presentation.form;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.formflow.meteo.app.presentation.form.lib.IdForm;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class IdFormTest {
  private static Validator validator;

  @BeforeAll
  public static void setUpValidator() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  @DisplayName("正常系: id=1はバリデーションエラーにならない")
  void testValidId() {
    IdForm form = new IdForm();
    form.setId(1);
    Set<ConstraintViolation<IdForm>> violations = validator.validate(form);
    assertTrue(violations.isEmpty(), "1以上のidはバリデーションエラーにならない");
  }

  @Test
  @DisplayName("異常系: id=0はバリデーションエラーになる")
  void testIdIsZero() {
    IdForm form = new IdForm();
    form.setId(0);
    Set<ConstraintViolation<IdForm>> violations = validator.validate(form);
    assertFalse(violations.isEmpty(), "id=0はバリデーションエラーになる");
    assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("IDは1文字以上でなければなりません。")));
  }

  @Test
  @DisplayName("異常系: idが負の値はバリデーションエラーになる")
  void testIdIsNegative() {
    IdForm form = new IdForm();
    form.setId(-1);
    Set<ConstraintViolation<IdForm>> violations = validator.validate(form);
    assertFalse(violations.isEmpty(), "idが負の値はバリデーションエラーになる");
    assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("IDは1文字以上でなければなりません。")));
  }

  @Test
  @DisplayName("異常系: id未設定（デフォルト0）はバリデーションエラーになる")
  void testIdIsDefault() {
    IdForm form = new IdForm();
    // int型のデフォルト値は0
    Set<ConstraintViolation<IdForm>> violations = validator.validate(form);
    assertFalse(violations.isEmpty(), "id未設定（デフォルト0）はバリデーションエラーになる");
    assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("IDは1文字以上でなければなりません。")));
  }
}
