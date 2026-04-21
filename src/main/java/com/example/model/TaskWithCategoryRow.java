package com.example.model;

import java.time.LocalDate;

/**
 * タスク＋カテゴリ名の一覧行。
 */
public record TaskWithCategoryRow(
    Integer id,
    String title,
    Integer categoryId,
    String categoryName,
    LocalDate dueDate,
    TaskStatus status
) {

}
