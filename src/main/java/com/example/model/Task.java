package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

/**
 * タスクの情報を保持するドメインモデル。 Lombokアノテーションにより、Getter/Setter、コンストラクタ等を自動生成します。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

  private Integer id;
  private String title;
  private String category;
  private LocalDate dueDate;

}