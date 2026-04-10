package com.example.model;

import java.util.List;

/**
 * ページングの結果（データリストと総件数）を保持するレコード。
 * サービス層からコントローラー層へ、1回の呼び出しで必要な情報を集約して返すために使用します。
 */
public record TaskPageResult(List<Task> tasks, long totalCount) {
}