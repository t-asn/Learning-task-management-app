package com.example.controller.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/** API エラー JSON（課題指定形式）。 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiErrorBody(
    int status,
    String error,
    String message,
    List<ApiFieldError> fieldErrors
) {

  public static ApiErrorBody badRequest(String message, List<ApiFieldError> fieldErrors) {
    return new ApiErrorBody(400, "BAD_REQUEST", message, fieldErrors);
  }

  public static ApiErrorBody notFound(String message) {
    return new ApiErrorBody(404, "NOT_FOUND", message, null);
  }

  public static ApiErrorBody internalServerError(String message) {
    return new ApiErrorBody(500, "INTERNAL_SERVER_ERROR", message, null);
  }
}
