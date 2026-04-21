package com.example.api;

import com.example.api.dto.ApiErrorBody;
import com.example.api.dto.ApiFieldError;
import com.example.exception.CategoryNotFoundException;
import com.example.exception.TaskNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * REST API 用の JSON エラーレスポンス。
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.example.api")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler {

  private final MessageSource messageSource;

  /**
   * コンストラクタ。
   *
   * @param messageSource メッセージソース
   */
  public ApiExceptionHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiErrorBody> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex) {
    List<ApiFieldError> fieldErrors = new ArrayList<>();
    for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
      // MessageSourceを使用してValidationMessages.propertiesからメッセージを取得
      String message = messageSource.getMessage(fe, LocaleContextHolder.getLocale());
      fieldErrors.add(new ApiFieldError(fe.getField(), message));
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiErrorBody.badRequest(fieldErrors));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ApiErrorBody> handleConstraintViolation(ConstraintViolationException ex) {
    List<ApiFieldError> fieldErrors = ex.getConstraintViolations().stream()
        .map(v -> {
          String path = v.getPropertyPath().toString();
          String field = path.contains(".") ? path.substring(path.lastIndexOf('.') + 1) : path;
          return new ApiFieldError(field, v.getMessage());
        })
        .toList();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiErrorBody.badRequest(fieldErrors));
  }

  @ExceptionHandler(HandlerMethodValidationException.class)
  public ResponseEntity<ApiErrorBody> handleHandlerMethodValidation(
      HandlerMethodValidationException ex) {
    List<ApiFieldError> fieldErrors = new ArrayList<>();
    ex.getParameterValidationResults().forEach(result ->
        result.getResolvableErrors().forEach(err -> {
          String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
          fieldErrors.add(new ApiFieldError(result.getMethodParameter().getParameterName(), message));
        })
    );
    if (fieldErrors.isEmpty()) {
      fieldErrors.add(new ApiFieldError("request", "入力値が不正です"));
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiErrorBody.badRequest(fieldErrors));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiErrorBody> handleNotReadable(HttpMessageNotReadableException ex) {
    var cause = ex.getMostSpecificCause();
    String rootMsg = cause != null ? cause.getMessage() : ex.getMessage();
    String field = (rootMsg != null && rootMsg.toLowerCase().contains("status"))
        ? "status" : "requestBody";
    List<ApiFieldError> fieldErrors = List.of(
        new ApiFieldError(field, "JSONの形式が不正です、または想定外の値です"));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiErrorBody.badRequest(fieldErrors));
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ApiErrorBody> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
    String name = ex.getName() != null ? ex.getName() : "parameter";
    log.debug("Type mismatch for field: {}, value: {}", name, ex.getValue());
    List<ApiFieldError> fieldErrors = List.of(
        new ApiFieldError(name, "入力値の型が正しくありません。"));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiErrorBody.badRequest(fieldErrors));
  }

  @ExceptionHandler({TaskNotFoundException.class, CategoryNotFoundException.class})
  public ResponseEntity<ApiErrorBody> handleNotFound(RuntimeException ex) {
    log.warn("404 Not Found (API): {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiErrorBody.notFound());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorBody> handleAny(Exception ex) {
    log.error("500 Internal Server Error (API)", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ApiErrorBody.internalServerError());
  }
}