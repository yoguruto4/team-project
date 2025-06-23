package com.formflow.meteo.app.presentation.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApprovalDecisionForm {

    private int id; // 承認のID（オプション）

    // 承認または却下の決定を表すフィールド
    @NotBlank(message = "決定は必須です。")
    private String decision; // "approve" または "reject"

    @NotBlank(message = "コメントは必須です。")
    private String comments; // コメントや理由

}
