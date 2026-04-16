package com.example.model;

import lombok.Getter;

/**
 * タスクの進捗ステータスを定義するEnum。
 */
@Getter
public enum TaskStatus {
  TODO,
  DOING,
  DONE;
}