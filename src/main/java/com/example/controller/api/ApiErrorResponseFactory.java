package com.example.controller.api;

import com.example.controller.api.dto.ApiErrorBody;
import com.example.controller.api.dto.ApiFieldError;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

/**
 * APIエラーレスポンスを生成するファクトリクラス。
 */
@Component
@RequiredArgsConstructor
public class ApiErrorResponseFactory {

  private final MessageSource messageSource;

  /**
   * バリデーションエラー(MethodArgumentNotValidException)からレスポンスを生成します。
   */
  public ResponseEntity<ApiErrorBody> buildBadRequestResponse(MethodArgumentNotValidException ex) {
    List<ApiFieldError> fieldErrors = new ArrayList<>();
    for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
      String message = messageSource.getMessage(fe, LocaleContextHolder.getLocale());
      fieldErrors.add(new ApiFieldError(fe.getField(), message));
    }
    return buildBadRequestResponse(fieldErrors);
  }

  /**
   * フィールドエラーリストからBAD_REQUESTレスポンスを生成します。
   */
  public ResponseEntity<ApiErrorBody> buildBadRequestResponse(List<ApiFieldError> fieldErrors) {
    String message = getMessage("api.error.bad_request");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ApiErrorBody.badRequest(message, fieldErrors));
  }

  /**
   * NOT_FOUNDレスポンスを生成します。
   */
  public ResponseEntity<ApiErrorBody> buildNotFoundResponse() {
    String message = getMessage("api.error.not_found");
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ApiErrorBody.notFound(message));
  }

  /**
   * INTERNAL_SERVER_ERRORレスポンスを生成します。
   */
  public ResponseEntity<ApiErrorBody> buildInternalServerErrorResponse() {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ApiErrorBody.internalServerError("予期しないエラーが発生しました"));
  }

  /**
   * 指定されたキーのメッセージを取得します。
   */
  public String getMessage(String code) {
    return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
  }

  /**
   * 指定されたメッセージソース定義からメッセージを取得します。
   */
  public String getMessage(org.springframework.context.MessageSourceResolvable resolvable) {
    return messageSource.getMessage(resolvable, LocaleContextHolder.getLocale());
  }
}
