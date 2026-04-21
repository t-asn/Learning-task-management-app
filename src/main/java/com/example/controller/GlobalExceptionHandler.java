package com.example.controller;

import com.example.exception.CategoryNotFoundException;
import com.example.exception.TaskNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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

  private final MessageSource messageSource;

  /**
   * コンストラクタ。
   *
   * @param messageSource メッセージソース
   */
  public GlobalExceptionHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ExceptionHandler({TaskNotFoundException.class, CategoryNotFoundException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String handleNotFound(Exception ex, Model model) {
    log.warn("404 Not Found 検知: {}", ex.getMessage());

    model.addAttribute("errorTitle", "404 Not Found");
    model.addAttribute("errorMessage", "指定されたリソースが見つかりませんでした。URLが正しいかご確認ください。");

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

    String detail;
    if (ex instanceof MethodArgumentTypeMismatchException) {
      detail = "URLのパラメータ（IDなど）の型が正しくありません。";
    } else {
      detail = ex.getMessage();
    }

    model.addAttribute("errorMessage", "不適切なリクエストです: " + detail);

    return "error/task-error";
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String handleGeneralException(Exception ex, Model model) {
    log.error("500 Internal Server Error: 予期せぬシステム例外が発生しました", ex);

    model.addAttribute("errorTitle", "500 System Error");
    model.addAttribute("errorMessage", "申し訳ございません。予期せぬエラーが発生しました。システム管理者に連絡してください。");

    return "error/task-error";
  }
}