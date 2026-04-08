package com.example.controller;

import com.example.exception.InvalidPageException;
import com.example.exception.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 全コントローラー共通のエラーハンドリングを行います。
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * 不正なリクエスト（ページ指定エラー、存在しないIDへのアクセス）を処理します。
   */
  @ExceptionHandler({TaskNotFoundException.class, InvalidPageException.class,
      MethodArgumentTypeMismatchException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handleBadRequest(Exception ex, Model model) {
    model.addAttribute("errorTitle", "400 Bad Request");
    String detail = (ex instanceof MethodArgumentTypeMismatchException)
        ? "URLのパラメータが不正です。" : ex.getMessage();
    model.addAttribute("errorMessage", "リクエストを処理できませんでした: " + detail);
    return "error/task-error";
  }

  /**
   * その他の予期せぬシステムエラーを処理します。
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String handleGeneralException(Exception ex, Model model) {
    model.addAttribute("errorTitle", "500 System Error");
    model.addAttribute("errorMessage", "予期せぬエラーが発生しました。");
    return "error/task-error";
  }
}