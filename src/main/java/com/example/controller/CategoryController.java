package com.example.controller;

import com.example.model.Category;
import com.example.service.CategoryService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * カテゴリ一覧・詳細画面の遷移を制御するコントローラー。
 */
@Controller
@RequestMapping("/categories")
public class CategoryController {

  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  /**
   * カテゴリ一覧画面を表示します。
   */
  @GetMapping
  public String list(Model model) {
    model.addAttribute("categories", categoryService.getAllCategories());
    return "categories/list";
  }

  /**
   * 指定されたカテゴリの詳細を表示します。
   */
  @GetMapping("/{id}")
  public String detail(@PathVariable Integer id, Model model) {
    Category category = categoryService.getCategoryById(id);
    model.addAttribute("category", category);
    return "categories/detail";
  }
}
