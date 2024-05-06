package com.example.nagoyameshi.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewForm {

    @NotNull(message = "評価を入力してください。")
    private Integer evaluation;

    @NotBlank(message = "コメントを入力してください。")
    private String comments;
}
