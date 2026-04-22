package com.example.controller.api;

import com.example.controller.api.dto.ApiErrorBody;
import com.example.controller.api.dto.ApiFieldError;
import com.example.exception.CategoryNotFoundException;
import com.example.exception.TaskNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

/**
 * REST API 用の例外ハンドラ。
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.example.controller.api")
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class ApiExceptionHandler {

  private final ApiErrorResponseFactory responseFactory;

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiErrorBody> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex) {
    return responseFactory.buildBadRequestResponse(ex);
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
    return responseFactory.buildBadRequestResponse(fieldErrors);
  }

  @ExceptionHandler(HandlerMethodValidationException.class)
  public ResponseEntity<ApiErrorBody> handleHandlerMethodValidation(
      HandlerMethodValidationException ex) {
    List<ApiFieldError> fieldErrors = new ArrayList<>();
    ex.getParameterValidationResults().forEach(result ->
        result.getResolvableErrors().forEach(err -> {
          String message = responseFactory.getMessage(err);
          fieldErrors.add(
              new ApiFieldError(result.getMethodParameter().getParameterName(), message));
        })
    );
    return responseFactory.buildBadRequestResponse(fieldErrors);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiErrorBody> handleNotReadable(HttpMessageNotReadableException ex) {
    var cause = ex.getMostSpecificCause();
    String rootMsg = cause != null ? cause.getMessage() : ex.getMessage();
    String field = (rootMsg != null && rootMsg.toLowerCase().contains("status"))
        ? "status" : "requestBody";
    List<ApiFieldError> fieldErrors = List.of(
        new ApiFieldError(field, "JSONの形式が不正です、または想定外の値です"));
    return responseFactory.buildBadRequestResponse(fieldErrors);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ApiErrorBody> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
    String name = ex.getName() != null ? ex.getName() : "parameter";
    List<ApiFieldError> fieldErrors = List.of(
        new ApiFieldError(name, "入力値の型が正しくありません。"));
    return responseFactory.buildBadRequestResponse(fieldErrors);
  }

  @ExceptionHandler({TaskNotFoundException.class, CategoryNotFoundException.class})
  public ResponseEntity<ApiErrorBody> handleNotFound(RuntimeException ex) {
    log.warn("404 Not Found (API): {}", ex.getMessage());
    return responseFactory.buildNotFoundResponse();
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorBody> handleAny(Exception ex) {
    log.error("500 Internal Server Error (API)", ex);
    return responseFactory.buildInternalServerErrorResponse();
  }
}
