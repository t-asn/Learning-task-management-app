package com.example.service;

import com.example.dao.CategoryDao;
import com.example.exception.CategoryNotFoundException;
import com.example.model.Category;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * カテゴリ管理のビジネスロジックを提供するサービスクラス。
 */
@Service
public class CategoryService {

  private final CategoryDao categoryDao;

  public CategoryService(CategoryDao categoryDao) {
    this.categoryDao = categoryDao;
  }

  /**
   * 全てのカテゴリを取得します。
   * @return カテゴリのリスト
   */
  public List<Category> findAll() {
    return (List<Category>) categoryDao.findAll();
  }

  /**
   * 指定されたIDのカテゴリを取得します。
   * @param id カテゴリID
   * @return カテゴリエンティティ
   * @throws CategoryNotFoundException 存在しないIDが指定された場合
   */
  public Category getCategoryById(Integer id) {
    return categoryDao.findById(id)
        .orElseThrow(() -> new CategoryNotFoundException("指定されたカテゴリは存在しません。ID: " + id));
  }
}