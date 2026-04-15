package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * カテゴリマスタエンティティ。 categories テーブルとマッピングされます。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("categories")
public class Category {

  /**
   * カテゴリID（主キー）
   */
  @Id
  private Integer id;

  /**
   * カテゴリ名
   */
  private String name;
}