package com.example.controller;

import com.example.model.*;
import com.example.service.CategoryService;
import com.example.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * タスク管理のメインコントローラー。
 * カテゴリマスタの参照およびステータス管理を制御します。
 */
@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

  private final TaskService taskService;
  private final CategoryService categoryService;

  /**
   * タスク一覧を表示します。
   * カテゴリIDをキー、名称を値としたMapを渡し、HTML側での名称解決を可能にします。
   */
  @GetMapping
  public String list(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "5") int size,
      Model model) {

    TaskPageResult result = taskService.getTasksByPage(page, size);

    // カテゴリマスタをMap形式で取得（ID -> 名称）
    List<Category> allCategories = categoryService.getAllCategories();
    Map<Integer, String> categoryMap = allCategories.stream()
        .collect(Collectors.toMap(Category::getId, Category::getName));

    model.addAttribute("tasks", result.tasks());
    model.addAttribute("categoryMap", categoryMap);
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", (int) Math.ceil((double) result.totalCount() / size));
    model.addAttribute("totalCount", result.totalCount());

    // ページネーション表示用
    int startRange = result.totalCount() == 0 ? 0 : (page - 1) * size + 1;
    int endRange = Math.min(page * size, (int) result.totalCount());
    model.addAttribute("startRange", startRange);
    model.addAttribute("endRange", endRange);

    return "tasks/list";
  }

  /**
   * ステータスを更新します。
   * リクエストパラメータを直接Enum（TaskStatus）で受け取ります。
   */
  @GetMapping("/update-status")
  public String updateStatus(
      @RequestParam Integer taskId,
      @RequestParam TaskStatus status,
      RedirectAttributes ra) {
    taskService.updateStatus(taskId, status);
    ra.addFlashAttribute("message", "ステータスを更新しました。");
    return "redirect:/tasks";
  }

  @GetMapping("/new")
  public String addForm(Model model) {
    model.addAttribute("task", new Task());
    return "tasks/form";
  }

  @GetMapping("/edit")
  public String editForm(@RequestParam Integer taskId, Model model) {
    model.addAttribute("task", taskService.getTaskById(taskId));
    return "tasks/form";
  }

  @PostMapping("/save")
  public String save(@Validated @ModelAttribute Task task, BindingResult result,
      RedirectAttributes ra) {
    if (result.hasErrors()) {
      return "tasks/form";
    }
    taskService.saveTask(task);
    ra.addFlashAttribute("message", "タスクを保存しました。");
    return "redirect:/tasks";
  }

  @GetMapping("/delete")
  public String delete(@RequestParam Integer taskId, RedirectAttributes ra) {
    taskService.deleteTask(taskId);
    ra.addFlashAttribute("message", "タスクを削除しました。");
    return "redirect:/tasks";
  }

  /**
   * フォーム（登録・編集）用のカテゴリリストをモデルに追加します。
   */
  @ModelAttribute("categories")
  public List<Category> categories() {
    return categoryService.getAllCategories();
  }
}