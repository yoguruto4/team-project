package com.formflow.meteo.app.presentation.form;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ApprovalDecisionFormのバリデーションテスト
 */
class ApprovalDecisionFormTest {
  private static Validator validator;

  @BeforeAll
  static void setUpValidator() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Nested
  @DisplayName("正常系")
  class NormalCase {
    @Test
    @DisplayName("全項目が正しく入力されている場合、バリデーションエラーなし")
    void testValidForm() {
      ApprovalDecisionForm form = new ApprovalDecisionForm();
      form.setId(1);
      form.setDecision("approve");
      form.setComments("承認します");
      Set<ConstraintViolation<ApprovalDecisionForm>> violations = validator.validate(form);
      assertTrue(violations.isEmpty(), "バリデーションエラーが発生しないこと");
    }
  }

  @Nested
  @DisplayName("異常系")
  class AbnormalCase {
    @Test
    @DisplayName("decisionが空の場合、バリデーションエラー")
    void testDecisionBlank() {
      ApprovalDecisionForm form = new ApprovalDecisionForm();
      form.setId(1);
      form.setDecision("");
      form.setComments("コメントあり");
      Set<ConstraintViolation<ApprovalDecisionForm>> violations = validator.validate(form);
      assertFalse(violations.isEmpty(), "decisionが空の場合はエラー");
      assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("decision")),
          "decisionフィールドのエラーが含まれること");
    }

    @Test
    @DisplayName("decisionがnullの場合、バリデーションエラー")
    void testDecisionNull() {
      ApprovalDecisionForm form = new ApprovalDecisionForm();
      form.setId(1);
      form.setDecision(null);
      form.setComments("コメントあり");
      Set<ConstraintViolation<ApprovalDecisionForm>> violations = validator.validate(form);
      assertFalse(violations.isEmpty(), "decisionがnullの場合はエラー");
      assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("decision")),
          "decisionフィールドのエラーが含まれること");
    }

    @Test
    @DisplayName("commentsが空の場合、バリデーションエラー")
    void testCommentsBlank() {
      ApprovalDecisionForm form = new ApprovalDecisionForm();
      form.setId(1);
      form.setDecision("approve");
      form.setComments("");
      Set<ConstraintViolation<ApprovalDecisionForm>> violations = validator.validate(form);
      assertFalse(violations.isEmpty(), "commentsが空の場合はエラー");
      assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("comments")),
          "commentsフィールドのエラーが含まれること");
    }

    @Test
    @DisplayName("commentsがnullの場合、バリデーションエラー")
    void testCommentsNull() {
      ApprovalDecisionForm form = new ApprovalDecisionForm();
      form.setId(1);
      form.setDecision("approve");
      form.setComments(null);
      Set<ConstraintViolation<ApprovalDecisionForm>> violations = validator.validate(form);
      assertFalse(violations.isEmpty(), "commentsがnullの場合はエラー");
      assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("comments")),
          "commentsフィールドのエラーが含まれること");
    }
  }
}
