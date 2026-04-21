package com.example.model;

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

  private String name;
}
