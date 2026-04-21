package com.example.controller;

import com.example.model.Category;
import com.example.model.Task;
import com.example.model.TaskPageResult;
import com.example.model.TaskStatus;
import com.example.service.CategoryService;
import com.example.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * タスク管理の画面制御を行うコントローラー。
 */
@Slf4j
@Controller
@RequestMapping("/tasks")
public class TaskController {

  private final TaskService taskService;
  private final CategoryService categoryService;

  public TaskController(TaskService taskService, CategoryService categoryService) {
    this.taskService = taskService;
    this.categoryService = categoryService;
  }

  /**
   * カテゴリリストを共通でモデルに追加します。
   */
  @ModelAttribute("categories")
  public List<Category> populateCategories() {
    List<Category> categories = categoryService.getAllCategories();
    if (categories == null) {
      log.error("CategoryService.getAllCategories() returned NULL.");
      return new ArrayList<>();
    }
    log.info("Categories loaded: {} items", categories.size());
    return categories;
  }

  @GetMapping
  public String list(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "5") int size,
      Model model) {
    TaskPageResult result = taskService.getTasksByPage(page, size);
    model.addAttribute("tasks", result.tasks());
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", (int) Math.ceil((double) result.totalCount() / size));
    model.addAttribute("totalCount", result.totalCount());
    return "tasks/list";
  }

  @GetMapping("/new")
  public String addForm(Model model) {
    model.addAttribute("task", new Task());
    model.addAttribute("categories", populateCategories());
    return "tasks/form";
  }

  @GetMapping("/edit")
  public String editForm(@RequestParam Integer taskId, Model model) {
    model.addAttribute("task", taskService.getTaskById(taskId));
    model.addAttribute("categories", populateCategories());
    return "tasks/form";
  }

  @PostMapping("/save")
  public String save(@Validated @ModelAttribute Task task, BindingResult result,
      Model model, RedirectAttributes ra) {

    if (result.hasErrors()) {
      model.addAttribute("categories", categoryService.getAllCategories());
      return "tasks/form";
    }

    try {
      taskService.saveTask(task);
      ra.addFlashAttribute("message", "タスクを保存しました。");
      return "redirect:/tasks"; // 保存後はリダイレクト
    } catch (Exception e) {
      log.error("保存失敗", e);
      model.addAttribute("errorMessage", "保存中にエラーが発生しました。");
      model.addAttribute("categories", categoryService.getAllCategories());
      return "tasks/form";
    }
  }

  @GetMapping("/delete")
  public String delete(@RequestParam Integer taskId, RedirectAttributes ra) {
    taskService.deleteTask(taskId);
    ra.addFlashAttribute("message", "タスクを削除しました。");
    return "redirect:/tasks";
  }

  @GetMapping("/update-status")
  public String updateStatus(
      @RequestParam Integer taskId,
      @RequestParam TaskStatus status,
      RedirectAttributes ra) {

    try {
      taskService.updateStatus(taskId, status);
      ra.addFlashAttribute("message", "ステータスを更新しました。");
    } catch (Exception e) {
      log.error("ステータス更新失敗", e);
      ra.addFlashAttribute("errorMessage", "ステータスの更新に失敗しました。");
    }
    return "redirect:/tasks";
  }
}