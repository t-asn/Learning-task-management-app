package com.example.controller;

import com.example.model.Category;
import com.example.model.Task;
import com.example.service.CategoryService;
import com.example.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * カテゴリ管理（CRUD）の画面制御を行うコントローラー。
 */
@Controller
@RequestMapping("/categories")
public class CategoryController {

  private final CategoryService categoryService;
  private final TaskService taskService; // タスク表示用に追加

  public CategoryController(CategoryService categoryService, TaskService taskService) {
    this.categoryService = categoryService;
    this.taskService = taskService;
  }

  /**
   * 一覧表示
   */
  @GetMapping
  public String list(Model model) {
    model.addAttribute("categories", categoryService.getAllCategories());
    // 新規登録用の空オブジェクトを渡す
    if (!model.containsAttribute("category")) {
      model.addAttribute("category", new Category());
    }
    return "categories/list";
  }

  /**
   * 詳細表示（＆編集）
   */
  @GetMapping("/{id}")
  public String detail(@PathVariable Integer id, Model model) {
    Category category = categoryService.getCategoryById(id);
    model.addAttribute("category", category);

    List<Task> tasks = taskService.getTasksByCategoryId(id);
    model.addAttribute("tasks", tasks);

    return "categories/detail";
  }

  /**
   * 新規登録
   */
  @PostMapping("/save")
  public String save(@Validated @ModelAttribute Category category, BindingResult result,
      RedirectAttributes ra, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("categories", categoryService.getAllCategories());
      return "categories/list";
    }
    categoryService.saveCategory(category);
    ra.addFlashAttribute("message", "カテゴリを登録しました。");
    return "redirect:/categories";
  }

  /**
   * 更新処理
   */
  @PostMapping("/{id}/update")
  public String update(@PathVariable Integer id, @Validated @ModelAttribute Category category,
      BindingResult result, RedirectAttributes ra, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("tasks", taskService.getTasksByCategoryId(id));
      return "categories/detail";
    }
    categoryService.updateCategory(id, category.getName());
    ra.addFlashAttribute("message", "カテゴリ名を更新しました。");
    return "redirect:/categories";
  }

  /**
   * 削除処理
   */
  @GetMapping("/{id}/delete")
  public String delete(@PathVariable Integer id, RedirectAttributes ra) {
    try {
      categoryService.deleteCategory(id);
      ra.addFlashAttribute("message", "カテゴリを削除しました。");
    } catch (Exception e) {
      ra.addFlashAttribute("errorMessage",
          "このカテゴリはタスクで使用されているため削除できません。");
    }
    return "redirect:/categories";
  }
}