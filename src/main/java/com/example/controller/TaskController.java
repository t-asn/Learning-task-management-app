package com.example.controller;

import com.example.model.Category;
import com.example.model.Task;
import com.example.model.TaskPageResult;
import com.example.model.TaskStatus;
import com.example.service.CategoryService;
import com.example.service.TaskService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * タスク画面（一覧・フォーム・削除など）。
 */
@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

  private final TaskService taskService;
  private final CategoryService categoryService;

  @GetMapping
  public String list(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "5") int size,
      Model model) {

    TaskPageResult result = taskService.getTasksByPage(page, size);

    List<Category> allCategories = categoryService.getAllCategories();
    Map<Integer, String> categoryMap = allCategories.stream()
        .collect(Collectors.toMap(Category::getId, Category::getName));

    model.addAttribute("tasks", result.tasks());
    model.addAttribute("categoryMap", categoryMap);
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", (int) Math.ceil((double) result.totalCount() / size));
    model.addAttribute("totalCount", result.totalCount());

    int startRange = result.totalCount() == 0 ? 0 : (page - 1) * size + 1;
    int endRange = Math.min(page * size, (int) result.totalCount());
    model.addAttribute("startRange", startRange);
    model.addAttribute("endRange", endRange);

    return "tasks/list";
  }

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
  public String save(
      @Validated @ModelAttribute Task task,
      BindingResult result,
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

  @ModelAttribute("categories")
  public List<Category> categories() {
    return categoryService.getAllCategories();
  }
}
