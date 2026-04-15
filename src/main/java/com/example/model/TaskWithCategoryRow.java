package com.example.model;

import java.time.LocalDate;

/**
 * SQLのJOIN結果を受け取るためのDTO。
 */
public record TaskWithCategoryRow(
    Integer id,
    String title,
    Integer categoryId,
    String categoryName,
    LocalDate dueDate
) {}