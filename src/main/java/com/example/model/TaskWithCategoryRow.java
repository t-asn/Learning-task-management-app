package com.example.model;

import java.time.LocalDate;

/**
 * JOIN結果を受け取るDTO。
 * statusをStringからTaskStatus(Enum)に変更しました。
 */
public record TaskWithCategoryRow(
    Integer id,
    String title,
    Integer categoryId,
    String categoryName,
    LocalDate dueDate,
    TaskStatus status
) {}