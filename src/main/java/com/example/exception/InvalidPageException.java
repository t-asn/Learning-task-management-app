package com.example.exception;

/**
 * ページネーションにおいて、範囲外のページ番号や不正な値が指定された場合にスローされる例外。
 */
public class InvalidPageException extends RuntimeException {

  /**
   * 例外メッセージを受け取るコンストラクタ。
   *
   * @param message エラー詳細メッセージ
   */
  public InvalidPageException(String message) {
    super(message);
  }
}