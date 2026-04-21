package com.example.api.dto;

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

  public static ApiErrorBody badRequest(List<ApiFieldError> fieldErrors) {
    return new ApiErrorBody(400, "BAD_REQUEST", "入力値が不正です", fieldErrors);
  }

  public static ApiErrorBody notFound() {
    return new ApiErrorBody(404, "NOT_FOUND", "指定されたリソースは存在しません", null);
  }

  public static ApiErrorBody internalServerError() {
    return new ApiErrorBody(500, "INTERNAL_SERVER_ERROR", "予期しないエラーが発生しました", null);
  }
}
