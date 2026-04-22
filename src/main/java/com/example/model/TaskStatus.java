package com.example.model;

import lombok.Getter;

@Getter
public enum TaskStatus {
  TODO("TODO"),
  DOING("着手"),
  DONE("完了");

  private final String displayName;

  TaskStatus(String displayName) {
    this.displayName = displayName;
  }
}
