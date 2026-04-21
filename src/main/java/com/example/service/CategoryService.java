package com.example.service;

import com.example.dao.CategoryDao;
import com.example.exception.CategoryNotFoundException;
import com.example.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * カテゴリ管理のビジネスロジック。
 */
@Service
@RequiredArgsConstructor
public class CategoryService { // インターフェースにしない場合は implements は不要です

  private final CategoryDao categoryDao;

  @Transactional(readOnly = true)
  public List<Category> getAllCategories() {
    return StreamSupport.stream(categoryDao.findAll().spliterator(), false)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Category getCategoryById(Integer id) {
    return categoryDao.findById(id)
        .orElseThrow(
            () -> new CategoryNotFoundException("指定されたカテゴリは存在しません。ID: " + id));
  }

  /**
   * 【追加】カテゴリを保存します
   */
  @Transactional
  public void saveCategory(Category category) {
    categoryDao.save(category);
  }

  /**
   * 【追加】カテゴリ名を更新します
   */
  @Transactional
  public void updateCategory(Integer id, String name) {
    Category category = getCategoryById(id);
    category.setName(name);
    categoryDao.save(category);
  }

  /**
   * 【追加】カテゴリを削除します
   */
  @Transactional
  public void deleteCategory(Integer id) {
    if (!categoryDao.existsById(id)) {
      throw new CategoryNotFoundException("削除対象のカテゴリが存在しません。ID: " + id);
    }
    categoryDao.deleteById(id);
  }

  /**
   * 【追加】findAll() を呼び出している箇所への互換性維持
   */
  public List<Category> findAll() {
    return getAllCategories();
  }
}