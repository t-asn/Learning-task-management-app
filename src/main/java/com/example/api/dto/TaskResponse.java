package com.example.api.dto;

import com.example.model.Task;
import com.example.model.TaskStatus;
import com.example.model.TaskWithCategoryRow;

import java.time.LocalDate;

/** タスク JSON 応答。 */
public record TaskResponse(
    Integer id,
    String title,
    Integer categoryId,
    String categoryName,
    LocalDate dueDate,
    TaskStatus status
) {

  public static TaskResponse fromRow(TaskWithCategoryRow row) {
    return new TaskResponse(
        row.id(),
        row.title(),
        row.categoryId(),
        row.categoryName(),
        row.dueDate(),
        row.status());
  }

  public static TaskResponse fromTask(Task task, String categoryName) {
    return new TaskResponse(
        task.getId(),
        task.getTitle(),
        task.getCategoryId(),
        categoryName,
        task.getDueDate(),
        task.getStatus());
  }
}
