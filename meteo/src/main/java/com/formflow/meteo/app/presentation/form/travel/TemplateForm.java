package com.formflow.meteo.app.presentation.form.travel;

import java.math.BigDecimal;

import com.formflow.meteo.app.infra.enums.TripType;
import com.formflow.meteo.app.presentation.form.travel.ItemForm.TransportationMethod;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.Data;

/**
 * 旅行経費申請テンプレート用のフォームクラス 旅行経費申請のテンプレートを作成するためのフィールドを定義
 * このクラスは、旅行経費申請のテンプレートに必要な情報を保持します。
 */

@Data
public class TemplateForm {

    /**
     * テンプレート名称（例：横浜⇔品川 通勤）
     */
    @NotBlank(message = "テンプレート名を入力してください。")
    @Size(max = 64, message = "テンプレート名は 64 文字以内で入力してください。")
    private String templateName;

    /**
     * 出発地
     */
    @NotNull(message = "出発地を入力してください。")
    @Size(max = 100)
    private String departure;

    /**
     * 到着地
     */
    @NotNull(message = "到着地を入力してください。")
    @Size(max = 100)
    private String arrival;

    /*
     * ===== 交通手段・経路・定額運賃 =====
     * 交通手段
     */
    @NotNull(message = "交通手段を選択してください。")
    private TransportationMethod transportationMethod;

    /**
     * 経路区分（片道／往復 など）
     */
    @NotNull(message = "経路を入力してください。")
    private TripType tripType;

    /**
     * 経由・ルート（任意）
     */
    @Size(max = 255)
    private String route;

    /**
     * 定額運賃
     */
    @NotNull(message = "金額を入力してください。")
    @Positive(message = "金額は 0 より大きい値を入力してください。")
    private BigDecimal cost;

}
