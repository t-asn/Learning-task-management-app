package com.example.exception;

/**
 * ページネーションにおいて、範囲外のページ番号が指定された場合にスローされる例外。
 */
public class InvalidPageException extends RuntimeException {

  public InvalidPageException(String message) {
    super(message);
  }
}
