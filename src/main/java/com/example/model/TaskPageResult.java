package com.example.model;

import java.util.List;

/**
 * ページング結果（一覧と総件数）を保持するコンテナ。
 */
public record TaskPageResult(List<TaskWithCategoryRow> tasks, long totalCount) {
}
