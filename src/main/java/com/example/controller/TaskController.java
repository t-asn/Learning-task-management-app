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
 * 一覧表示、新規登録、編集、削除の各リクエストを処理します。
 */
@Controller
@RequestMapping("/tasks") // ベースパスを /tasks に設定
public class TaskController {

  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  /**
   * 各画面で共通して使用するカテゴリリストをModelに登録する。
   * @return カテゴリ名のリスト
   */
  @ModelAttribute("categories")
  public List<String> categories() {
    return List.of("Java", "Spring", "その他");
  }

  /**
   * タスク一覧画面を表示する。
   * @param model 画面に渡すデータを格納するModelオブジェクト
   * @return タスク一覧画面のビュー名 (tasks/list)
   */
  @GetMapping
  public String list(Model model) {
    model.addAttribute("tasks", taskService.getAllTasks());
    return "tasks/list";
  }

  /**
   * タスク新規登録画面を表示する。
   * @param model 新規作成用の空のTaskオブジェクト
   * @return タスク登録画面のビュー名 (tasks/form)
   */
  @GetMapping("/new")
  public String addForm(Model model) {
    model.addAttribute("task", new Task());
    return "tasks/form";
  }

  /**
   * タスクの保存処理を行い、一覧画面へリダイレクトする。
   * @param task フォームデータ
   * @param ra フラッシュメッセージ用
   * @return 一覧画面へのリダイレクト (/tasks)
   */
  @PostMapping("/save")
  public String save(@Validated @ModelAttribute Task task, BindingResult result, RedirectAttributes ra) {
    if (result.hasErrors()) {
      ra.addFlashAttribute("message", "入力内容に誤りがあります。");
      return "tasks/form";
    }
    
    taskService.saveTask(task);
    ra.addFlashAttribute("message", "タスクを保存しました");
    return "redirect:/tasks";
  }

  /**
   * タスク編集画面を表示する。
   * @param taskId 編集対象のタスクID
   * @param model 取得したタスクデータを格納するModel
   * @param ra リダイレクト時にメッセージを渡すためのオブジェクト
   * @return タスク編集画面のビュー名、または一覧画面へのリダイレクト
   */
  @GetMapping("/edit")
  public String editForm(@RequestParam Integer taskId, Model model, RedirectAttributes ra) {
    try {
      Task task = taskService.getTaskById(taskId)
          .orElseThrow(() -> new IllegalArgumentException("指定されたタスクが見つかりません。ID: " + taskId));

      model.addAttribute("task", task);
      return "tasks/form";

    } catch (IllegalArgumentException e) {
      ra.addFlashAttribute("message", e.getMessage());
      return "redirect:/tasks";
    }
  }

  /**
   * タスクを削除し、一覧画面へリダイレクトする。
   * @param taskId 削除対象のID
   * @param ra 削除完了メッセージ用
   * @return 一覧画面へのリダイレクト (/tasks)
   */
  @GetMapping("/delete")
  public String delete(@RequestParam Integer taskId, RedirectAttributes ra) {
    taskService.deleteTask(taskId);
    ra.addFlashAttribute("message", "タスクを削除しました");
    return "redirect:/tasks"; // /tasks へリダイレクト
  }
}