package com.example.exception;

/**
 * 指定されたタスクが見つからない場合にスローされる例外クラス。 RuntimeExceptionを継承することで、呼び出し元での明示的な try-catch を不要にします。
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