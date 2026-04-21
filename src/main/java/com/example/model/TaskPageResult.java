package com.example.model;

import java.util.List;

/**
 * ページング用の一覧＋総件数。
 */
public record TaskPageResult(List<TaskWithCategoryRow> tasks, long totalCount) {

}
