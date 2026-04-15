package com.example.exception;

/**
 * 指定されたタスクがデータベースに存在しない場合にスローされる例外。
 */
public class TaskNotFoundException extends RuntimeException {

  public TaskNotFoundException(String message) {
    super(message);
  }
}