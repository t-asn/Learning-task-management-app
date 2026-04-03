package com.example.exception;

/**
 * 指定されたタスクが見つからない場合にスローされる例外クラス。
 */
public class TaskNotFoundException extends RuntimeException {

  /**
   * コンストラクタ。
   *
   * @param message エラーメッセージ
   */
  public TaskNotFoundException(String message) {
    super(message);
  }
}