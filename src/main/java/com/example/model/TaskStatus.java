package com.example.model;

import lombok.Getter;

@Getter
public enum TaskStatus {
  TODO, DOING, DONE;

  public static TaskStatus of(String statusName) {
    try {
      return TaskStatus.valueOf(statusName.toUpperCase());
    } catch (IllegalArgumentException | NullPointerException e) {
      throw new IllegalArgumentException("不正なステータスです：" + statusName);
    }
  }
}