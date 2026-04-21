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
 * タスク管理のメインコントローラー。
 *
 * <p>一覧表示、登録/編集、削除、ステータス更新などの画面遷移を制御します。</p>
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
   *
   * @param page 1始まりのページ番号（省略時は1）
   * @param size 1ページあたりの件数（省略時は5）
   * @param model 画面表示用モデル
   * @return 一覧画面のテンプレート名
   */
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

  /**
   * ステータスを更新します。
   * リクエストパラメータを {@link TaskStatus} として受け取ります。
   *
   * @param taskId 更新対象のタスクID
   * @param status 更新後のステータス
   * @param ra リダイレクト用フラッシュ属性
   * @return 一覧画面へのリダイレクト
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

  /**
   * タスク登録フォームを表示します。
   *
   * @param model 画面表示用モデル
   * @return フォーム画面のテンプレート名
   */
  @GetMapping("/new")
  public String addForm(Model model) {
    model.addAttribute("task", new Task());
    return "tasks/form";
  }

  /**
   * タスク編集フォームを表示します。
   *
   * @param taskId 編集対象のタスクID
   * @param model 画面表示用モデル
   * @return フォーム画面のテンプレート名
   */
  @GetMapping("/edit")
  public String editForm(@RequestParam Integer taskId, Model model) {
    model.addAttribute("task", taskService.getTaskById(taskId));
    return "tasks/form";
  }

  /**
   * タスクを保存します（新規作成/更新）。
   *
   * @param task 入力値（バリデーション対象）
   * @param result バリデーション結果
   * @param ra リダイレクト用フラッシュ属性
   * @return 入力エラー時はフォーム、成功時は一覧へリダイレクト
   */
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

  /**
   * タスクを削除します。
   *
   * @param taskId 削除対象のタスクID
   * @param ra リダイレクト用フラッシュ属性
   * @return 一覧画面へのリダイレクト
   */
  @GetMapping("/delete")
  public String delete(@RequestParam Integer taskId, RedirectAttributes ra) {
    taskService.deleteTask(taskId);
    ra.addFlashAttribute("message", "タスクを削除しました。");
    return "redirect:/tasks";
  }

  /**
   * フォーム（登録・編集）用のカテゴリリストをモデルに追加します。
   *
   * <p>全ハンドラメソッド実行前に呼ばれ、テンプレート側から {@code categories} として参照できます。</p>
   *
   * @return カテゴリ一覧
   */
  @ModelAttribute("categories")
  public List<Category> categories() {
    return categoryService.getAllCategories();
  }
}
