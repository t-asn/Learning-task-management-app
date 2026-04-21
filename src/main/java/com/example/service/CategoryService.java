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

@Service
@RequiredArgsConstructor
public class CategoryService {

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

  public List<Category> findAll() {
    return null;
  }
}
