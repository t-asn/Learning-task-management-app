package com.example.controller;

import com.example.exception.TaskNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /**
   * TaskNotFoundException（タスクが見つからない例外）が発生した際の処理。 自作のエラー画面にエラー内容を渡して表示します。
   *
   * @param ex　発生した例外オブジェクト
   *
   * @param model 画面にデータを渡すためのModel
   * @return エラー表示用HTMLのパス (error/task-error)
   */
  @ExceptionHandler(TaskNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String handleTaskNotFoundException(TaskNotFoundException ex, Model model) {
    // 例外メッセージ（「指定されたタスクは存在しません：taskId=...」）を画面に渡す
    model.addAttribute("errorTitle", "404 Not Found");
    model.addAttribute("errorMessage", ex.getMessage());

    return "error/task-error";
  }

  /**
   * その他の予期せぬ例外が発生した場合の汎用ハンドラ。
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String handleGeneralException(Exception ex, Model model) {
    logger.error("An unexpected error occurred: {}", ex.getMessage(), ex);
    model.addAttribute("errorTitle", "System Error");
    model.addAttribute("errorMessage",
        "予期せぬエラーが発生しました。システム管理者に連絡してください。");
    return "error/task-error";
  }
}