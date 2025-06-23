package com.formflow.meteo.app.presentation.form.travel;

import com.formflow.meteo.app.infra.enums.TripType;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("TemplateFormのバリデーションテスト")
class TemplateFormTest {
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
    @DisplayName("全項目が正しい場合、バリデーションエラーなし")
    void validAllFields() {
      TemplateForm form = new TemplateForm();
      form.setTemplateName("横浜⇔品川 通勤");
      form.setDeparture("横浜");
      form.setArrival("品川");
      form.setTransportationMethod(ItemForm.TransportationMethod.TRAIN);
      form.setTripType(TripType.片道);
      form.setRoute("京浜東北線");
      form.setCost(BigDecimal.valueOf(500));
      Set<ConstraintViolation<TemplateForm>> violations = validator.validate(form);
      assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("routeがnullでもバリデーションエラーなし（任意項目）")
    void validWhenRouteIsNull() {
      TemplateForm form = new TemplateForm();
      form.setTemplateName("テンプレート");
      form.setDeparture("A");
      form.setArrival("B");
      form.setTransportationMethod(ItemForm.TransportationMethod.BUS);
      form.setTripType(TripType.往復);
      form.setRoute(null);
      form.setCost(BigDecimal.valueOf(1));
      Set<ConstraintViolation<TemplateForm>> violations = validator.validate(form);
      assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("routeが255文字でもバリデーションエラーなし")
    void validWhenRouteIs255Chars() {
      TemplateForm form = new TemplateForm();
      form.setTemplateName("テンプレート");
      form.setDeparture("A");
      form.setArrival("B");
      form.setTransportationMethod(ItemForm.TransportationMethod.BUS);
      form.setTripType(TripType.往復);
      form.setRoute("a".repeat(255));
      form.setCost(BigDecimal.valueOf(1));
      Set<ConstraintViolation<TemplateForm>> violations = validator.validate(form);
      assertThat(violations).isEmpty();
    }
  }

  @Nested
  @DisplayName("異常系")
  class InvalidCases {
    @Test
    @DisplayName("必須項目がnullの場合、バリデーションエラー")
    void invalidWhenRequiredFieldsNull() {
      TemplateForm form = new TemplateForm();
      Set<ConstraintViolation<TemplateForm>> violations = validator.validate(form);
      assertThat(violations).hasSize(6);
    }

    @Test
    @DisplayName("templateNameが空文字の場合、バリデーションエラー")
    void invalidWhenTemplateNameBlank() {
      TemplateForm form = new TemplateForm();
      form.setTemplateName("");
      form.setDeparture("横浜");
      form.setArrival("品川");
      form.setTransportationMethod(ItemForm.TransportationMethod.TRAIN);
      form.setTripType(TripType.片道);
      form.setCost(BigDecimal.valueOf(500));
      Set<ConstraintViolation<TemplateForm>> violations = validator.validate(form);
      assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("templateName"));
    }

    @Test
    @DisplayName("templateNameが65文字以上の場合、バリデーションエラー")
    void invalidWhenTemplateNameTooLong() {
      TemplateForm form = new TemplateForm();
      form.setTemplateName("a".repeat(65));
      form.setDeparture("横浜");
      form.setArrival("品川");
      form.setTransportationMethod(ItemForm.TransportationMethod.TRAIN);
      form.setTripType(TripType.片道);
      form.setCost(BigDecimal.valueOf(500));
      Set<ConstraintViolation<TemplateForm>> violations = validator.validate(form);
      assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("templateName"));
    }

    @Test
    @DisplayName("departureが100文字超の場合、バリデーションエラー")
    void invalidWhenDepartureTooLong() {
      TemplateForm form = new TemplateForm();
      form.setTemplateName("テンプレート");
      form.setDeparture("a".repeat(101));
      form.setArrival("B");
      form.setTransportationMethod(ItemForm.TransportationMethod.BUS);
      form.setTripType(TripType.往復);
      form.setCost(BigDecimal.valueOf(1));
      Set<ConstraintViolation<TemplateForm>> violations = validator.validate(form);
      assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("departure"));
    }

    @Test
    @DisplayName("arrivalが100文字超の場合、バリデーションエラー")
    void invalidWhenArrivalTooLong() {
      TemplateForm form = new TemplateForm();
      form.setTemplateName("テンプレート");
      form.setDeparture("A");
      form.setArrival("a".repeat(101));
      form.setTransportationMethod(ItemForm.TransportationMethod.BUS);
      form.setTripType(TripType.往復);
      form.setCost(BigDecimal.valueOf(1));
      Set<ConstraintViolation<TemplateForm>> violations = validator.validate(form);
      assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("arrival"));
    }

    @Test
    @DisplayName("routeが256文字以上の場合、バリデーションエラー")
    void invalidWhenRouteTooLong() {
      TemplateForm form = new TemplateForm();
      form.setTemplateName("テンプレート");
      form.setDeparture("A");
      form.setArrival("B");
      form.setTransportationMethod(ItemForm.TransportationMethod.BUS);
      form.setTripType(TripType.往復);
      form.setRoute("a".repeat(256));
      form.setCost(BigDecimal.valueOf(1));
      Set<ConstraintViolation<TemplateForm>> violations = validator.validate(form);
      assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("route"));
    }

    @Test
    @DisplayName("costが0以下の場合、バリデーションエラー")
    void invalidWhenCostZeroOrNegative() {
      TemplateForm form = new TemplateForm();
      form.setTemplateName("横浜⇔品川 通勤");
      form.setDeparture("横浜");
      form.setArrival("品川");
      form.setTransportationMethod(ItemForm.TransportationMethod.TRAIN);
      form.setTripType(TripType.片道);
      form.setCost(BigDecimal.ZERO);
      Set<ConstraintViolation<TemplateForm>> violations = validator.validate(form);
      assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("cost"));
    }

    @Test
    @DisplayName("costがマイナスの場合、バリデーションエラー")
    void invalidWhenCostNegative() {
      TemplateForm form = new TemplateForm();
      form.setTemplateName("横浜⇔品川 通勤");
      form.setDeparture("横浜");
      form.setArrival("品川");
      form.setTransportationMethod(ItemForm.TransportationMethod.TRAIN);
      form.setTripType(TripType.片道);
      form.setCost(BigDecimal.valueOf(-1));
      Set<ConstraintViolation<TemplateForm>> violations = validator.validate(form);
      assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("cost"));
    }
  }
}
