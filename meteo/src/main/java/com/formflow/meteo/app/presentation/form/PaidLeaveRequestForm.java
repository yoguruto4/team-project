package com.formflow.meteo.app.presentation.form;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 有給休暇申請フォーム
 *
 * paid_leave_request テーブルの <code>paid_leave_days</code>,
 * <code>leave_reason</code>, <code>date_of_leave</code> に対応します。<br>
 * ログインユーザー情報と document 生成は Controller／Service 側で補完してください。
 */

@Data
public class PaidLeaveRequestForm {

    /** 取得日 */
    @NotNull(message = "取得日を入力してください。")
    @FutureOrPresent(message = "過去の日付は選択できません。")
    private LocalDate dateOfLeave;

    /** 取得日数（0.5 日単位） */
    @NotNull(message = "取得日数を入力してください。")
    @DecimalMin(value = "0.5", inclusive = true, message = "0.5 日以上で入力してください。")
    @Digits(integer = 2, fraction = 1, message = "0.5 日刻みで入力してください。")
    private BigDecimal paidLeaveDays;

    /** 取得理由 */
    @NotBlank(message = "理由を入力してください。")
    @Size(max = 255, message = "理由は 255 文字以内で入力してください。")
    private String leaveReason;

}
