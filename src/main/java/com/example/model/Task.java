package com.example.model;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class Task {

  /**
   * タスクID（新規登録時はnullを許容）
   */
  private Integer id;

  /**
   * タスクのタイトル。 空文字、空白のみを禁止し、50文字以内であることを保証します。
   */
  @NotBlank(message = "タイトルを入力してください")
  @Size(max = 50, message = "タイトルは50文字以内で入力してください")
  private String title;

  /**
   * タスクのカテゴリ。 未選択を防ぐために、画面側で制御できない場合はここにも制約を追加可能です。
   */
  @NotBlank(message = "カテゴリを選択してください")
  private String category;

  /**
   * 完了期限。 必須入力であり、かつ「今日以降」の日付のみを許可します。
   */
  @NotNull(message = "期限を入力してください")
  @FutureOrPresent(message = "過去の日付は登録できません。本日以降の日付を選択してください")
  private LocalDate dueDate;

}