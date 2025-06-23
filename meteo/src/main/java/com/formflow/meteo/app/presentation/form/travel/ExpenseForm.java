package com.formflow.meteo.app.presentation.form.travel;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/*申請書種別選択画面のバインド用フォーム
 * ユーザーが「どの申請書を作成するか」を選択するフェーズで使用。
 */
@Data
public class ExpenseForm {

    @NotEmpty(message = "交通費明細を1件以上入力してください。")
    private List<com.formflow.meteo.app.presentation.form.travel.ExpenseForm> items = new ArrayList<>();

    /*--- accessor ---*/

}
