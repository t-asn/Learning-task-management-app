package com.example.controller.mvc;

import com.example.exception.ApiExceptionHandler;
import com.example.exception.CategoryNotFoundException;
import com.example.exception.TaskNotFoundException;
import lombok.RequiredArgsConstructor;
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
 * Thymeleaf 用の共通例外ハンドラ（JSON API は {@link ApiExceptionHandler}）。
 */
@Slf4j
@ControllerAdvice(basePackages = "com.example.controller.mvc")
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  private final MessageSource messageSource;

  @ExceptionHandler({TaskNotFoundException.class, CategoryNotFoundException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String handleNotFound(Exception ex, Model model) {
    log.warn("404 Not Found 検知: {}", ex.getMessage());

    model.addAttribute("errorTitle", "404 Not Found");
    model.addAttribute("errorMessage",
        messageSource.getMessage("ui.error.not_found", null, LocaleContextHolder.getLocale()));

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

    String detailMessage;
    if (ex instanceof MethodArgumentTypeMismatchException) {
      detailMessage = messageSource.getMessage("ui.error.type_mismatch", null,
          LocaleContextHolder.getLocale());
    } else {
      detailMessage = ex.getMessage();
    }

    model.addAttribute("errorMessage",
        messageSource.getMessage("ui.error.bad_request", null, LocaleContextHolder.getLocale())
            + ": " + detailMessage);

    return "error/task-error";
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String handleGeneralException(Exception ex, Model model) {
    log.error("500 Internal Server Error: 予期せぬシステム例外が発生しました", ex);

    model.addAttribute("errorTitle", "500 System Error");
    model.addAttribute("errorMessage",
        messageSource.getMessage("ui.error.internal_server_error", null,
            LocaleContextHolder.getLocale()));

    return "error/task-error";
  }
}
