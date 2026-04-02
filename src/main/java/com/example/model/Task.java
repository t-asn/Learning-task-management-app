package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

/**
 * タスク情報を保持するドメインモデル（エンティティ）。 PostgreSQLの tasks テーブルとマッピングされ、入力バリデーションルールを定義します。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("tasks")
public class Task {

  /**
   * タスクID。 DBの主キー（シリアル値）に対応します。新規登録時は null となり、DB保存時に自動採番されます。
   */
  @Id
  private Integer id;

  /**
   * タスクのタイトル。 必須入力であり、50文字以内の制限があります。
   */
  @NotBlank(message = "タイトルを入力してください")
  @Size(max = 50, message = "タイトルは50文字以内で入力してください")
  private String title;

  /**
   * タスクのカテゴリ。 UI上の選択肢（Java, Spring, その他など）と連動します。
   */
  @NotBlank(message = "カテゴリを選択してください")
  private String category;

  /**
   * タスクの完了期限。 必須入力であり、過去の日付は許可されません。
   */
  @NotNull(message = "期限を入力してください")
  @FutureOrPresent(message = "過去の日付は登録できません。本日以降の日付を選択してください")
  private LocalDate dueDate;
}