package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

/**
 * タスクの情報を保持するドメインモデル。 Lombokによるコード生成と、Bean Validationによる入力チェックルールを定義しています。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("tasks")
public class Task {

  /**
   * タスクID（主キー）。新規登録時は null。
   */
  @Id
  private Integer id;

  /**
   * タスクのタイトル（必須、50文字以内）。
   */
  @NotBlank(message = "タイトルを入力してください")
  @Size(max = 50, message = "タイトルは50文字以内で入力してください")
  private String title;

  /**
   * タスクのカテゴリ（必須）。
   */
  @NotBlank(message = "カテゴリを選択してください")
  private String category;

  /**
   * タスクの完了期限（必須、過去日は不可）。
   */
  @NotNull(message = "期限を入力してください")
  @FutureOrPresent(message = "過去の日付は登録できません。本日以降の日付を選択してください")
  private LocalDate dueDate;
}