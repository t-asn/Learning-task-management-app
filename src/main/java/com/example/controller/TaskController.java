package com.example.controller;

import com.example.model.Category;
import com.example.model.Task;
import com.example.model.TaskPageResult;
import com.example.service.CategoryService;
import com.example.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tasks")
public class TaskController {

  private final TaskService taskService;
  private final CategoryService categoryService;

  public TaskController(TaskService taskService, CategoryService categoryService) {
    this.taskService = taskService;
    this.categoryService = categoryService;
  }

  @ModelAttribute("categories")
  public List<Category> categories() {
    return categoryService.findAll();
  }

  @GetMapping
  public String list(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "5") int size,
      Model model) {

    TaskPageResult result = taskService.getTasksByPage(page, size);

    // 表示デグレ防止用
    Map<Integer, String> categoryMap = categoryService.findAll().stream()
        .collect(Collectors.toMap(Category::getId, Category::getName));

    model.addAttribute("tasks", result.tasks());
    model.addAttribute("categoryMap", categoryMap);
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", (int) Math.ceil((double) result.totalCount() / size));
    model.addAttribute("totalCount", result.totalCount());
    model.addAttribute("startRange", result.totalCount() == 0 ? 0 : (page - 1) * size + 1);
    model.addAttribute("endRange", Math.min(page * size, (int) result.totalCount()));

    return "tasks/list";
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

  @GetMapping("/update-status")
  public String updateStatus(
      @RequestParam Integer taskId,
      @RequestParam String status,
      RedirectAttributes ra) {

    taskService.updateStatus(taskId, status);
    ra.addFlashAttribute("message", "ステータスを更新しました。");
    return "redirect:/tasks";
  }
}