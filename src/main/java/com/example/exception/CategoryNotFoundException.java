package com.example.exception;

/**
 * 指定されたカテゴリがデータベースに存在しない場合にスローされる例外。
 */
public class CategoryNotFoundException extends RuntimeException {

  /**
   * @param message 例外メッセージ
   */
  public CategoryNotFoundException(String message) {
    super(message);
  }
}
