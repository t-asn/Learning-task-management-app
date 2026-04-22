package com.example.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.FutureOrPresent;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDate;
import lombok.Data;

/**
 * タスクエンティティ。
 */
@Data
@Table("tasks")
public class Task {

  @Id
  private Integer id;

  @NotBlank(message = "{task.title.notblank}")
  @Size(max = 100, message = "{task.title.size}")
  private String title;

  @NotNull(message = "{task.dueDate.notnull}")
  @FutureOrPresent(message = "{task.dueDate.past}")
  private LocalDate dueDate;

  @NotNull(message = "{task.categoryId.notnull}")
  private Integer categoryId;

  // 初期値をセットすることで、新規登録時の「非NULL制約」違反を防ぎます
  private TaskStatus status = TaskStatus.TODO;
}
