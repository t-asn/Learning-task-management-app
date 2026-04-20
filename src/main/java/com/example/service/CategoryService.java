package com.example.service;

import com.example.dao.CategoryDao;
import com.example.exception.CategoryNotFoundException;
import com.example.model.Category;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
   */
  @Transactional(readOnly = true)
  public List<Category> getAllCategories() {
    return StreamSupport.stream(categoryDao.findAll().spliterator(), false)
        .collect(Collectors.toList());
  }

  /**
   * 指定されたIDのカテゴリを取得します。
   *
   * @param id カテゴリID
   * @return カテゴリエンティティ
   * @throws CategoryNotFoundException 存在しないIDが指定された場合
   */
  @Transactional(readOnly = true)
  public Category getCategoryById(Integer id) {
    return categoryDao.findById(id)
        .orElseThrow(() -> new CategoryNotFoundException("指定されたカテゴリは存在しません。ID: " + id));
  }
}
