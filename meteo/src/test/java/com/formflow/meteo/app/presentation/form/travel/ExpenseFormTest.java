package com.formflow.meteo.app.presentation.form.travel;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ExpenseFormのバリデーションテスト")
class ExpenseFormTest {
  private static Validator validator;

  @BeforeAll
  static void setUpValidator() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Nested
  @DisplayName("正常系")
  class ValidCases {
    @Test
    @DisplayName("itemsに1件以上の明細がある場合、バリデーションエラーなし")
    void validWhenItemsNotEmpty() {
      ExpenseForm form = new ExpenseForm();
      form.getItems().add(new ExpenseForm());
      Set<ConstraintViolation<ExpenseForm>> violations = validator.validate(form);
      assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("itemsに複数件の明細がある場合、バリデーションエラーなし")
    void validWhenItemsHasMultiple() {
      ExpenseForm form = new ExpenseForm();
      form.getItems().add(new ExpenseForm());
      form.getItems().add(new ExpenseForm());
      Set<ConstraintViolation<ExpenseForm>> violations = validator.validate(form);
      assertThat(violations).isEmpty();
    }
  }

  @Nested
  @DisplayName("異常系")
  class InvalidCases {
    @Test
    @DisplayName("itemsが空の場合、バリデーションエラー")
    void invalidWhenItemsEmpty() {
      ExpenseForm form = new ExpenseForm();
      form.setItems(new ArrayList<>());
      Set<ConstraintViolation<ExpenseForm>> violations = validator.validate(form);
      assertThat(violations).isNotEmpty();
      assertThat(violations.iterator().next().getMessage()).isEqualTo("交通費明細を1件以上入力してください。");
    }

    @Test
    @DisplayName("itemsがnullの場合、バリデーションエラー")
    void invalidWhenItemsNull() {
      ExpenseForm form = new ExpenseForm();
      form.setItems(null);
      Set<ConstraintViolation<ExpenseForm>> violations = validator.validate(form);
      assertThat(violations).isNotEmpty();
      assertThat(violations.iterator().next().getMessage()).isEqualTo("交通費明細を1件以上入力してください。");
    }

    @Test
    @DisplayName("itemsにnull要素が含まれる場合、バリデーションエラーなし（コレクション自体の制約のみ）")
    void validWhenItemsContainsNullElement() {
      ExpenseForm form = new ExpenseForm();
      form.getItems().add(null);
      Set<ConstraintViolation<ExpenseForm>> violations = validator.validate(form);
      // @NotEmptyはコレクションの要素数のみを見るため、null要素でもエラーにならない
      assertThat(violations).isEmpty();
    }
  }
}
