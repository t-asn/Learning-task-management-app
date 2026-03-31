package com.example.controller;

import com.example.model.Task;
import com.example.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
  public String list(Model model) {
    model.addAttribute("tasks", taskService.getAllTasks());
    return "tasks/list";
  }

  @GetMapping("/new")
  public String addForm(Model model) {
    model.addAttribute("task", new Task());
    return "tasks/form";
  }

  @PostMapping("/save")
  public String save(@ModelAttribute Task task, RedirectAttributes ra) {
    taskService.saveTask(task);
    ra.addFlashAttribute("message", "タスクを保存しました");
    return "redirect:/tasks";
  }

  @GetMapping("/edit")
  public String editForm(@RequestParam Integer taskId, Model model) {
    model.addAttribute("task", taskService.getTaskById(taskId));
    return "tasks/form";
  }

  @GetMapping("/delete")
  public String delete(@RequestParam Integer taskId, RedirectAttributes ra) {
    taskService.deleteTask(taskId);
    ra.addFlashAttribute("message", "タスクを削除しました");
    return "redirect:/tasks";
  }
}