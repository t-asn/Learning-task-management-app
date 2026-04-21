package com.example.controller;

import com.example.exception.CategoryNotFoundException;
import com.example.exception.TaskNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Thymeleaf 用の共通例外ハンドラ（JSON API は {@link com.example.api.ApiExceptionHandler}）。
 */
@Slf4j
@ControllerAdvice(basePackages = "com.example.controller")
public class GlobalExceptionHandler {

  @ExceptionHandler({TaskNotFoundException.class, CategoryNotFoundException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String handleNotFound(Exception ex, Model model) {
    log.warn("404 Not Found 検知: {}", ex.getMessage());

    model.addAttribute("errorTitle", "404 Not Found");
    model.addAttribute("errorMessage", "指定されたリソースは存在しません。");

    return "error/task-error";
  }

  @ExceptionHandler({
      IllegalArgumentException.class,
      MethodArgumentTypeMismatchException.class
  })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handleBadRequest(Exception ex, Model model) {
    log.warn("400 Bad Request 検知: {}", ex.getMessage());

    model.addAttribute("errorTitle", "400 Bad Request");
    String detail = (ex instanceof MethodArgumentTypeMismatchException)
        ? "URLのパラメータが不正です。" : ex.getMessage();
    model.addAttribute("errorMessage", "リクエストを処理できませんでした: " + detail);

    return "error/task-error";
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String handleGeneralException(Exception ex, Model model) {
    log.error("500 Internal Server Error: 予期せぬシステム例外が発生しました", ex);

    model.addAttribute("errorTitle", "500 System Error");
    model.addAttribute("errorMessage", "予期せぬエラーが発生しました。");

    return "error/task-error";
  }
}
