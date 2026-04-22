package com.example.controller.api.dto;

import com.example.model.Task;
import com.example.model.TaskStatus;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * POST/PUT の JSON ボディ。
 */
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

  /**
   * TaskRequest から Task モデルへ変換します。
   *
   * @param id タスクID（新規登録時は null）
   * @return Task モデル
   */
  public Task toModel(Integer id) {
    Task t = new Task();
    t.setId(id);
    t.setTitle(this.title());
    t.setCategoryId(this.categoryId());
    t.setDueDate(this.dueDate());
    t.setStatus(this.status());
    return t;
  }
}
