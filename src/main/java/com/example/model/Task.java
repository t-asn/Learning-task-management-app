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

  @NotNull(message = "カテゴリを選択してください")
  private Integer categoryId;

  @NotNull(message = "期限を入力してください")
  @FutureOrPresent(message = "過去の日付は登録できません")
  private LocalDate dueDate;
  
  private String status = "TODO";
}