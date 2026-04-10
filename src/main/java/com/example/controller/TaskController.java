package com.example.controller;

import com.example.model.Task;
import com.example.model.TaskPageResult;
import com.example.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @ModelAttribute("categories")
  public List<String> categories() {
    return List.of("Java", "Spring", "その他");
  }

  @GetMapping
  public String list(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "5") int size,
      Model model) {

    // 修正点：サービスを一度呼び出し、結果オブジェクトからデータを取得
    TaskPageResult result = taskService.getTasksByPage(page, size);
    List<Task> tasks = result.tasks();
    long totalCount = result.totalCount();

    model.addAttribute("tasks", tasks);
    model.addAttribute("currentPage", page);
    model.addAttribute("pageSize", size);
    model.addAttribute("totalPages", (int) Math.ceil((double) totalCount / size));
    model.addAttribute("totalCount", totalCount);
    model.addAttribute("startRange", totalCount == 0 ? 0 : (page - 1) * size + 1);
    model.addAttribute("endRange", Math.min(page * size, (int) totalCount));

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
}