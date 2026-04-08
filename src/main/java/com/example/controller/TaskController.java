package com.example.controller;

import com.example.model.Task;
import com.example.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

/**
 * タスク管理機能の画面制御を行うコントローラー。
 */
@Controller
@RequestMapping("/tasks")
public class TaskController {

  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  /**
   * 各画面で共通して使用するカテゴリリスト。
   */
  @ModelAttribute("categories")
  public List<String> categories() {
    return List.of("Java", "Spring", "その他");
  }

  /**
   * ページネーション対応のタスク一覧画面を表示します。
   */
  @GetMapping
  public String list(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "5") int size,
      Model model) {

    long totalCount = taskService.getTotalCount();
    List<Task> tasks = taskService.getTasksByPage(page, size);

    model.addAttribute("tasks", tasks);
    model.addAttribute("currentPage", page);
    model.addAttribute("pageSize", size);
    model.addAttribute("totalPages", (int) Math.ceil((double) totalCount / size));
    model.addAttribute("totalCount", totalCount);
    model.addAttribute("startRange", totalCount == 0 ? 0 : (page - 1) * size + 1);
    model.addAttribute("endRange", Math.min(page * size, (int) totalCount));

    return "tasks/list";
  }

  /**
   * 新規登録画面を表示します。
   */
  @GetMapping("/new")
  public String addForm(Model model) {
    model.addAttribute("task", new Task());
    return "tasks/form";
  }

  /**
   * 編集画面を表示します。
   */
  @GetMapping("/edit")
  public String editForm(@RequestParam Integer taskId, Model model) {
    model.addAttribute("task", taskService.getTaskById(taskId));
    return "tasks/form";
  }

  /**
   * タスクの保存（新規登録または更新）を行います。
   */
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

  /**
   * タスクを削除します。
   */
  @GetMapping("/delete")
  public String delete(@RequestParam Integer taskId, RedirectAttributes ra) {
    taskService.deleteTask(taskId);
    ra.addFlashAttribute("message", "タスクを削除しました。");
    return "redirect:/tasks";
  }
}