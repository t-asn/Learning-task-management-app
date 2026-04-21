package com.example.exception;

/**
 * カテゴリが存在しないとき。
 */
public class CategoryNotFoundException extends RuntimeException {

  public CategoryNotFoundException(String message) {
    super(message);
  }
}
