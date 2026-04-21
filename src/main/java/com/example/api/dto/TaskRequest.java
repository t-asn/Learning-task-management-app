package com.example.api.dto;

import com.example.model.TaskStatus;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/** POST/PUT の JSON ボディ。 */
public record TaskRequest(
    @NotBlank(message = "必須です")
    @Size(max = 50, message = "50文字以内で入力してください")
    String title,

    @NotNull(message = "必須です")
    Integer categoryId,

    @NotNull(message = "必須です")
    @FutureOrPresent(message = "過去の日付は登録できません")
    LocalDate dueDate,

    @NotNull(message = "必須です")
    TaskStatus status
) {
}
