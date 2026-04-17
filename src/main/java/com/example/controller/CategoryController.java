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
   * Service側のメソッド名変更に合わせて修正しました。
   */
  @GetMapping
  public String list(Model model) {
    // findAll() から getAllCategories() へ修正
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

    // 【補足】もし詳細画面で「そのカテゴリに属するタスク一覧」を出したい場合は、
    // 今後 taskService.findByCategoryId(id) などを呼んで model に追加する形になります。

    return "categories/detail";
  }
}