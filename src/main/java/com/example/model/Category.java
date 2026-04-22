package com.example.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * {@code categories} テーブル対応エンティティ。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("categories")
public class Category {

  @Id
  private Integer id;

  @NotBlank(message = "カテゴリ名を入力してください。")
  @Size(max = 50, message = "カテゴリ名は50文字以内で入力してください。")
  private String name;
}
