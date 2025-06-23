package com.formflow.meteo.app.presentation.form.travel;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.Data;

/**
 * 交通費申請の 1 明細を表すフォームクラス。
 */
@Data
public class ItemForm {

    /**
     * 交通手段 (TRAIN / BUS / TAXI / OTHER) を表す列挙型。 TRAIN: 電車 BUS: バス TAXI: タクシー
     */
    @NotNull(message = "交通手段を選択してください。")
    private TransportationMethod transportationMethod;

    @NotNull(message = "経路を入力してください。")
    private String tripType; // 経路区分（片道／往復 など）

    @NotNull(message = "路線が入力されていません。")
    @Size(max = 100, message = "路線は100文字以内で入力してください。")
    private String route;

    @NotNull(message = "出発地を入力してください。")
    @Size(max = 100)
    private String departure;

    @NotNull(message = "到着地を入力してください。")
    @Size(max = 100)
    private String arrival;

    @NotNull(message = "金額を入力してください。")
    @Positive(message = "金額は0より大きい値を入力してください。")
    private BigDecimal amount;

    @NotNull(message = "利用日を入力してください。")
    @PastOrPresent(message = "利用日は過去または現在の日付を入力してください。")
    private LocalDate usageDate;

    public enum TransportationMethod {
        TRAIN, BUS, FLIGHT, TAXI, OTHER
    }
}
