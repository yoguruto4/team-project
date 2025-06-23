package com.formflow.meteo.app.presentation.form;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * PaidLeaveRequestFormのバリデーションテスト
 */
@DisplayName("PaidLeaveRequestForm Validation Test")
class PaidLeaveRequestFormTest {
  private Validator validator;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  private PaidLeaveRequestForm createValidForm() {
    PaidLeaveRequestForm form = new PaidLeaveRequestForm();
    form.setDateOfLeave(LocalDate.now());
    form.setPaidLeaveDays(BigDecimal.valueOf(1.0));
    form.setLeaveReason("体調不良のため");
    return form;
  }

  @Nested
  @DisplayName("取得日(dateOfLeave)のバリデーション")
  class DateOfLeaveValidation {
    @Test
    @DisplayName("今日または未来日なら正常")
    void validDate() {
      PaidLeaveRequestForm form = createValidForm();
      form.setDateOfLeave(LocalDate.now().plusDays(1));
      Set<ConstraintViolation<PaidLeaveRequestForm>> violations = validator.validate(form);
      assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("nullの場合はエラー")
    void nullDate() {
      PaidLeaveRequestForm form = createValidForm();
      form.setDateOfLeave(null);
      Set<ConstraintViolation<PaidLeaveRequestForm>> violations = validator.validate(form);
      assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("dateOfLeave"));
    }

    @Test
    @DisplayName("過去日付の場合はエラー")
    void pastDate() {
      PaidLeaveRequestForm form = createValidForm();
      form.setDateOfLeave(LocalDate.now().minusDays(1));
      Set<ConstraintViolation<PaidLeaveRequestForm>> violations = validator.validate(form);
      assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("dateOfLeave"));
    }
  }

  @Nested
  @DisplayName("取得日数(paidLeaveDays)のバリデーション")
  class PaidLeaveDaysValidation {
    @Test
    @DisplayName("0.5以上・小数点1桁なら正常")
    void validPaidLeaveDays() {
      PaidLeaveRequestForm form = createValidForm();
      form.setPaidLeaveDays(BigDecimal.valueOf(0.5));
      Set<ConstraintViolation<PaidLeaveRequestForm>> violations = validator.validate(form);
      assertThat(violations).isEmpty();
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("nullの場合はエラー")
    void nullPaidLeaveDays(BigDecimal value) {
      PaidLeaveRequestForm form = createValidForm();
      form.setPaidLeaveDays(value);
      Set<ConstraintViolation<PaidLeaveRequestForm>> violations = validator.validate(form);
      assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("paidLeaveDays"));
    }

    @Test
    @DisplayName("0.5未満はエラー")
    void lessThanMin() {
      PaidLeaveRequestForm form = createValidForm();
      form.setPaidLeaveDays(BigDecimal.valueOf(0.4));
      Set<ConstraintViolation<PaidLeaveRequestForm>> violations = validator.validate(form);
      assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("paidLeaveDays"));
    }

    @Test
    @DisplayName("小数点2桁以上はエラー")
    void moreThanOneDecimal() {
      PaidLeaveRequestForm form = createValidForm();
      form.setPaidLeaveDays(new BigDecimal("1.11"));
      Set<ConstraintViolation<PaidLeaveRequestForm>> violations = validator.validate(form);
      assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("paidLeaveDays"));
    }
  }

  @Nested
  @DisplayName("取得理由(leaveReason)のバリデーション")
  class LeaveReasonValidation {
    @Test
    @DisplayName("空白でなく255文字以内なら正常")
    void validLeaveReason() {
      PaidLeaveRequestForm form = createValidForm();
      form.setLeaveReason("有給理由");
      Set<ConstraintViolation<PaidLeaveRequestForm>> violations = validator.validate(form);
      assertThat(violations).isEmpty();
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", "   " })
    @DisplayName("空白またはnullはエラー")
    void blankOrNullReason(String value) {
      PaidLeaveRequestForm form = createValidForm();
      form.setLeaveReason(value);
      Set<ConstraintViolation<PaidLeaveRequestForm>> violations = validator.validate(form);
      assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("leaveReason"));
    }

    @Test
    @DisplayName("256文字以上はエラー")
    void overMaxLength() {
      PaidLeaveRequestForm form = createValidForm();
      form.setLeaveReason("a".repeat(256));
      Set<ConstraintViolation<PaidLeaveRequestForm>> violations = validator.validate(form);
      assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("leaveReason"));
    }
  }
}
