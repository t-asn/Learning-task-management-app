package com.example.controller;

import com.example.exception.TaskNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * アプリケーション全体の例外をハンドリングするクラス。
 * @ControllerAdvice により、全コントローラーで発生した例外をこのクラスで捕捉します。
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * TaskNotFoundException（タスクが見つからない例外）が発生した際の処理。
   * 自作のエラー画面にエラー内容を渡して表示します。
   * * @param ex 発生した例外オブジェクト
   * @param model 画面にデータを渡すためのModel
   * @return エラー表示用HTMLのパス (error/task-error)
   */
  @ExceptionHandler(TaskNotFoundException.class)
  public String handleTaskNotFoundException(TaskNotFoundException ex, Model model) {
    // 例外メッセージ（「指定されたタスクは存在しません：taskId=...」）を画面に渡す
    model.addAttribute("errorTitle", "404 Not Found");
    model.addAttribute("errorMessage", ex.getMessage());

    // src/main/resources/templates/error/task-error.html を表示
    return "error/task-error";
  }

  /**
   * その他の予期せぬ例外が発生した場合の汎用ハンドラ（必要に応じて追加）。
   */
  @ExceptionHandler(Exception.class)
  public String handleGeneralException(Exception ex, Model model) {
    model.addAttribute("errorTitle", "System Error");
    model.addAttribute("errorMessage", "予期せぬエラーが発生しました。システム管理者に連絡してください。");
    return "error/task-error";
  }
}