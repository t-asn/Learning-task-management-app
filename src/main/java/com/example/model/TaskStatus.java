package com.example.model;

import lombok.Getter;

@Getter
public enum TaskStatus {
  NOT_STARTED("未着手"),
  DOING("着手"),
  DONE("完了");

  /**
   * 大文字小文字を区別しない。
   */
  private final String displayName;

  TaskStatus(String displayName) {
    this.displayName = displayName;
  }
}
