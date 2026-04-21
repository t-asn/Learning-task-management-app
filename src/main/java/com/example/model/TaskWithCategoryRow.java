package com.example.model;

import java.time.LocalDate;

/**
 * タスクとカテゴリを JOIN した結果を受け取る DTO（読み取り専用）。
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
