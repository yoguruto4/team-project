package com.formflow.meteo.app.presentation.form.lib;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class IdForm {
    @Min(value = 1, message = "IDは1文字以上でなければなりません。")
    private int id;
}
