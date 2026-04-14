package com.example.controller;

import com.example.exception.CategoryNotFoundException;
import com.example.exception.InvalidPageException;
import com.example.exception.TaskNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 全コントローラー共通のエラーハンドリングを行うクラス。
 * ユーザー向けには安全なエラー画面を提供しつつ、
 * サーバー側（インフラ）には調査用の詳細なログを出力します。
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * 不正なリクエスト（存在しないID、範囲外のページなど）を処理し、400エラー画面を表示します。
   * ユーザー起因のエラーのため、WARN（警告）レベルでログを残します。
   *
   * @param ex 発生した例外
   * @param model 画面へのデータ渡し用モデル
   * @return エラー画面のテンプレートパス
   */
  @ExceptionHandler({
      TaskNotFoundException.class,
      CategoryNotFoundException.class,
      InvalidPageException.class,
      MethodArgumentTypeMismatchException.class
  })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handleBadRequest(Exception ex, Model model) {
    // [ログ出力] 何の不正リクエストがあったかを1行で記録（スタックトレースは不要）
    log.warn("400 Bad Request 検知: {}", ex.getMessage());

    model.addAttribute("errorTitle", "400 Bad Request");
    String detail = (ex instanceof MethodArgumentTypeMismatchException)
        ? "URLのパラメータが不正です。" : ex.getMessage();
    model.addAttribute("errorMessage", "リクエストを処理できませんでした: " + detail);

    return "error/task-error";
  }

  /**
   * その他の予期せぬシステムエラー（DB接続失敗、SQL構文エラーなど）を処理し、500エラー画面を表示します。
   * システム障害の可能性があるため、ERROR（エラー）レベルで詳細なスタックトレースを記録します。
   *
   * @param ex 発生した例外
   * @param model 画面へのデータ渡し用モデル
   * @return エラー画面のテンプレートパス
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String handleGeneralException(Exception ex, Model model) {
    // [ログ出力] 開発・運用時の原因究明のため、エラー内容とスタックトレースを詳細に記録
    log.error("500 Internal Server Error: 予期せぬシステム例外が発生しました", ex);

    model.addAttribute("errorTitle", "500 System Error");
    model.addAttribute("errorMessage", "予期せぬエラーが発生しました。");

    return "error/task-error";
  }
}