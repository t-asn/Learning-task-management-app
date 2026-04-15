package com.example.model;

import java.time.LocalDate;

/**
 * SQLのJOIN結果を受け取るための読み取り専用クラス。
 */
public record TaskWithCategoryRow(
    Integer id,
    String title,
    Integer categoryId,
    String categoryName,
    LocalDate dueDate
) {}