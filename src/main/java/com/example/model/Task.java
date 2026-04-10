package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("tasks")
public class Task {

  @Id
  private Integer id;

  @NotBlank(message = "タイトルを入力してください")
  @Size(max = 50, message = "タイトルは50文字以内で入力してください")
  private String title;

  @NotBlank(message = "カテゴリを選択してください")
  private String category;

  @NotNull(message = "期限を入力してください")
  @FutureOrPresent(message = "過去の日付は登録できません。本日以降の日付を選択してください")
  private LocalDate dueDate;
}