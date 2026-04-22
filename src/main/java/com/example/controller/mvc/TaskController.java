package com.example.controller.mvc;

import com.example.model.Category;
import com.example.model.Task;
import com.example.model.TaskPageResult;
import com.example.model.TaskStatus;
import com.example.service.CategoryService;
import com.example.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * タスク管理の画面制御を行うコントローラー。
 */
@Slf4j
@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

  private final TaskService taskService;
  private final CategoryService categoryService;

  /**
   * カテゴリリストを共通でモデルに追加します。
   * これにより、各メソッド内で個別に categories を Model に詰める必要がなくなります。
   */
  @ModelAttribute("categories")
  public List<Category> populateCategories() {
    return categoryService.getAllCategories();
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
      @RequestParam TaskStatus status,
      RedirectAttributes ra) {

    taskService.updateStatus(taskId, status);
    ra.addFlashAttribute("message", "ステータスを更新しました。");
    return "redirect:/tasks";
  }
}
