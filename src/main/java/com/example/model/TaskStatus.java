package com.example.model;

import lombok.Getter;

@Getter
public enum TaskStatus {

  TODO,
  DOING,
  DONE;

  /**
   * 文字列から {@link TaskStatus} を生成します（大文字小文字は区別しません）。
   *
   * @param statusName ステータス名（例: todo / TODO）
   * @return 変換後のステータス
   * @throws IllegalArgumentException 変換できない場合
   */
  public static TaskStatus of(String statusName) {
    try {
      return TaskStatus.valueOf(statusName.toUpperCase());
    } catch (IllegalArgumentException | NullPointerException e) {
      throw new IllegalArgumentException("不正なステータスです：" + statusName);
    }
  }
}
