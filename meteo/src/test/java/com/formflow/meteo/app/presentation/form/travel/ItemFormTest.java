package com.formflow.meteo.app.presentation.form.travel;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ItemFormのバリデーションテスト")
class ItemFormTest {
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
      ItemForm form = new ItemForm();
      form.setTransportationMethod(ItemForm.TransportationMethod.TRAIN);
      form.setTripType("片道");
      form.setRoute("山手線");
      form.setDeparture("新宿");
      form.setArrival("渋谷");
      form.setAmount(BigDecimal.valueOf(500));
      form.setUsageDate(LocalDate.now());
      Set<ConstraintViolation<ItemForm>> violations = validator.validate(form);
      assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("route, departure, arrivalが100文字の場合、バリデーションエラーなし")
    void validWhenFieldsAre100Chars() {
      String s = "a".repeat(100);
      ItemForm form = new ItemForm();
      form.setTransportationMethod(ItemForm.TransportationMethod.BUS);
      form.setTripType("往復");
      form.setRoute(s);
      form.setDeparture(s);
      form.setArrival(s);
      form.setAmount(BigDecimal.valueOf(1));
      form.setUsageDate(LocalDate.now());
      Set<ConstraintViolation<ItemForm>> violations = validator.validate(form);
      assertThat(violations).isEmpty();
    }
  }

  @Nested
  @DisplayName("異常系")
  class InvalidCases {
    @Test
    @DisplayName("必須項目がnullの場合、バリデーションエラー")
    void invalidWhenRequiredFieldsNull() {
      ItemForm form = new ItemForm();
      Set<ConstraintViolation<ItemForm>> violations = validator.validate(form);
      assertThat(violations).hasSize(7);
    }

    @Test
    @DisplayName("金額が0以下の場合、バリデーションエラー")
    void invalidWhenAmountZeroOrNegative() {
      ItemForm form = new ItemForm();
      form.setTransportationMethod(ItemForm.TransportationMethod.BUS);
      form.setTripType("往復");
      form.setRoute("中央線");
      form.setDeparture("東京");
      form.setArrival("八王子");
      form.setAmount(BigDecimal.ZERO);
      form.setUsageDate(LocalDate.now());
      Set<ConstraintViolation<ItemForm>> violations = validator.validate(form);
      assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("amount"));
    }

    @Test
    @DisplayName("金額がマイナスの場合、バリデーションエラー")
    void invalidWhenAmountNegative() {
      ItemForm form = new ItemForm();
      form.setTransportationMethod(ItemForm.TransportationMethod.BUS);
      form.setTripType("往復");
      form.setRoute("中央線");
      form.setDeparture("東京");
      form.setArrival("八王子");
      form.setAmount(BigDecimal.valueOf(-1));
      form.setUsageDate(LocalDate.now());
      Set<ConstraintViolation<ItemForm>> violations = validator.validate(form);
      assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("amount"));
    }

    @Test
    @DisplayName("利用日が未来の場合、バリデーションエラー")
    void invalidWhenUsageDateFuture() {
      ItemForm form = new ItemForm();
      form.setTransportationMethod(ItemForm.TransportationMethod.BUS);
      form.setTripType("往復");
      form.setRoute("中央線");
      form.setDeparture("東京");
      form.setArrival("八王子");
      form.setAmount(BigDecimal.valueOf(1000));
      form.setUsageDate(LocalDate.now().plusDays(1));
      Set<ConstraintViolation<ItemForm>> violations = validator.validate(form);
      assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("usageDate"));
    }

    @Test
    @DisplayName("routeが100文字超の場合、バリデーションエラー")
    void invalidWhenRouteTooLong() {
      ItemForm form = new ItemForm();
      form.setTransportationMethod(ItemForm.TransportationMethod.TRAIN);
      form.setTripType("片道");
      form.setRoute("a".repeat(101));
      form.setDeparture("新宿");
      form.setArrival("渋谷");
      form.setAmount(BigDecimal.valueOf(500));
      form.setUsageDate(LocalDate.now());
      Set<ConstraintViolation<ItemForm>> violations = validator.validate(form);
      assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("route"));
    }

    @Test
    @DisplayName("departureが100文字超の場合、バリデーションエラー")
    void invalidWhenDepartureTooLong() {
      ItemForm form = new ItemForm();
      form.setTransportationMethod(ItemForm.TransportationMethod.TRAIN);
      form.setTripType("片道");
      form.setRoute("山手線");
      form.setDeparture("a".repeat(101));
      form.setArrival("渋谷");
      form.setAmount(BigDecimal.valueOf(500));
      form.setUsageDate(LocalDate.now());
      Set<ConstraintViolation<ItemForm>> violations = validator.validate(form);
      assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("departure"));
    }

    @Test
    @DisplayName("arrivalが100文字超の場合、バリデーションエラー")
    void invalidWhenArrivalTooLong() {
      ItemForm form = new ItemForm();
      form.setTransportationMethod(ItemForm.TransportationMethod.TRAIN);
      form.setTripType("片道");
      form.setRoute("山手線");
      form.setDeparture("新宿");
      form.setArrival("a".repeat(101));
      form.setAmount(BigDecimal.valueOf(500));
      form.setUsageDate(LocalDate.now());
      Set<ConstraintViolation<ItemForm>> violations = validator.validate(form);
      assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("arrival"));
    }

    @Test
    @DisplayName("routeがnullの場合、バリデーションエラー")
    void invalidWhenRouteNull() {
      ItemForm form = new ItemForm();
      form.setTransportationMethod(ItemForm.TransportationMethod.TRAIN);
      form.setTripType("片道");
      form.setRoute(null);
      form.setDeparture("新宿");
      form.setArrival("渋谷");
      form.setAmount(BigDecimal.valueOf(500));
      form.setUsageDate(LocalDate.now());
      Set<ConstraintViolation<ItemForm>> violations = validator.validate(form);
      assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("route"));
    }

    @Test
    @DisplayName("departureがnullの場合、バリデーションエラー")
    void invalidWhenDepartureNull() {
      ItemForm form = new ItemForm();
      form.setTransportationMethod(ItemForm.TransportationMethod.TRAIN);
      form.setTripType("片道");
      form.setRoute("山手線");
      form.setDeparture(null);
      form.setArrival("渋谷");
      form.setAmount(BigDecimal.valueOf(500));
      form.setUsageDate(LocalDate.now());
      Set<ConstraintViolation<ItemForm>> violations = validator.validate(form);
      assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("departure"));
    }

    @Test
    @DisplayName("arrivalがnullの場合、バリデーションエラー")
    void invalidWhenArrivalNull() {
      ItemForm form = new ItemForm();
      form.setTransportationMethod(ItemForm.TransportationMethod.TRAIN);
      form.setTripType("片道");
      form.setRoute("山手線");
      form.setDeparture("新宿");
      form.setArrival(null);
      form.setAmount(BigDecimal.valueOf(500));
      form.setUsageDate(LocalDate.now());
      Set<ConstraintViolation<ItemForm>> violations = validator.validate(form);
      assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("arrival"));
    }

    @Test
    @DisplayName("amountがnullの場合、バリデーションエラー")
    void invalidWhenAmountNull() {
      ItemForm form = new ItemForm();
      form.setTransportationMethod(ItemForm.TransportationMethod.TRAIN);
      form.setTripType("片道");
      form.setRoute("山手線");
      form.setDeparture("新宿");
      form.setArrival("渋谷");
      form.setAmount(null);
      form.setUsageDate(LocalDate.now());
      Set<ConstraintViolation<ItemForm>> violations = validator.validate(form);
      assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("amount"));
    }

    @Test
    @DisplayName("usageDateがnullの場合、バリデーションエラー")
    void invalidWhenUsageDateNull() {
      ItemForm form = new ItemForm();
      form.setTransportationMethod(ItemForm.TransportationMethod.TRAIN);
      form.setTripType("片道");
      form.setRoute("山手線");
      form.setDeparture("新宿");
      form.setArrival("渋谷");
      form.setAmount(BigDecimal.valueOf(500));
      form.setUsageDate(null);
      Set<ConstraintViolation<ItemForm>> violations = validator.validate(form);
      assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("usageDate"));
    }

    @Test
    @DisplayName("tripTypeがnullの場合、バリデーションエラー")
    void invalidWhenTripTypeNull() {
      ItemForm form = new ItemForm();
      form.setTransportationMethod(ItemForm.TransportationMethod.TRAIN);
      form.setTripType(null);
      form.setRoute("山手線");
      form.setDeparture("新宿");
      form.setArrival("渋谷");
      form.setAmount(BigDecimal.valueOf(500));
      form.setUsageDate(LocalDate.now());
      Set<ConstraintViolation<ItemForm>> violations = validator.validate(form);
      assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("tripType"));
    }

    @Test
    @DisplayName("transportationMethodがnullの場合、バリデーションエラー")
    void invalidWhenTransportationMethodNull() {
      ItemForm form = new ItemForm();
      form.setTransportationMethod(null);
      form.setTripType("片道");
      form.setRoute("山手線");
      form.setDeparture("新宿");
      form.setArrival("渋谷");
      form.setAmount(BigDecimal.valueOf(500));
      form.setUsageDate(LocalDate.now());
      Set<ConstraintViolation<ItemForm>> violations = validator.validate(form);
      assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("transportationMethod"));
    }
  }
}
