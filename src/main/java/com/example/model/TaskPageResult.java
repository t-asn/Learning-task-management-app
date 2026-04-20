package com.example.model;

import java.util.List;

/**
 * ページングの結果（JOIN済みのRowデータと総件数）を保持するコンテナ。
 */
public record TaskPageResult(List<TaskWithCategoryRow> tasks, long totalCount) {
}
