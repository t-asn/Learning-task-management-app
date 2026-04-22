package com.example.controller.api.dto;

import com.example.model.Task;
import com.example.model.TaskStatus;
import com.example.model.TaskWithCategoryRow;

import java.time.LocalDate;

/**
 * タスクの REST API 用レスポンス DTO。
 */
public record TaskResponse(
    Integer id,
    String title,
    Integer categoryId,
    String categoryName,
    LocalDate dueDate,
    TaskStatus status
) {

  /**
   * TaskWithCategoryRow から TaskResponse を生成します。
   *
   * @param row データベースから取得したカテゴリ情報付きタスク行
   * @return TaskResponse インスタンス
   */
  public static TaskResponse fromRow(TaskWithCategoryRow row) {
    return new TaskResponse(
        row.id(),
        row.title(),
        row.categoryId(),
        row.categoryName(),
        row.dueDate(),
        row.status() // TaskWithCategoryRow に status() メソッドがあることを前提とします
    );
  }

  /**
   * Task エンティティとカテゴリ名から TaskResponse を生成します。
   *
   * @param task         タスクエンティティ
   * @param categoryName カテゴリ名
   * @return TaskResponse インスタンス
   */
  public static TaskResponse fromTask(Task task, String categoryName) {
    return new TaskResponse(
        task.getId(),
        task.getTitle(),
        task.getCategoryId(),
        categoryName,
        task.getDueDate(),
        task.getStatus()
    );
  }
}
