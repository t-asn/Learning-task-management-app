package com.example.exception;

/**
 * 指定されたタスクがデータベースに存在しない場合（更新・削除・検索時）にスローされる例外。
 */
public class TaskNotFoundException extends RuntimeException {

  /**
   * 例外メッセージを受け取るコンストラクタ。
   *
   * @param message エラー詳細メッセージ
   */
  public TaskNotFoundException(String message) {
    super(message);
  }
}