package com.example.exception;

/**
 * タスクが存在しないとき。
 */
public class TaskNotFoundException extends RuntimeException {

  public TaskNotFoundException(String message) {
    super(message);
  }
}
