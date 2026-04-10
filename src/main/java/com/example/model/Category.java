package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;
import java.util.Set;

/**
 * カテゴリマスタエンティティ。 categories テーブルとマッピングされ、1対多のタスクを保持します。
 */
@Data
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

  /**
   * このカテゴリに紐づくタスクの集合。
   *
   * @MappedCollection により、tasksテーブルの category_id をキーとして 該当するタスク一覧を自動的に取得します。
   */
  @MappedCollection(idColumn = "category_id")
  private Set<Task> tasks;
}