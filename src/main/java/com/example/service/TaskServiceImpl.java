package com.example.service;

import com.example.exception.InvalidPageException;
import com.example.exception.TaskNotFoundException;
import com.example.model.Task;
import com.example.model.TaskPageResult;
import com.example.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * TaskService の実装クラス。
 * ページネーションの計算や、カテゴリ連携を含めたビジネスルールを制御します。
 */
@Service
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;
  private final CategoryService categoryService;

  public TaskServiceImpl(TaskRepository taskRepository, CategoryService categoryService) {
    this.taskRepository = taskRepository;
    this.categoryService = categoryService;
  }

 
  public TaskPageResult getTasksByPage(int page, int size) {
    long totalCount = taskRepository.countAll();
    int totalPages = (int) Math.ceil((double) totalCount / size);

    if (totalCount > 0 && (page < 1 || page > totalPages)) {
      throw new InvalidPageException("page=" + page);
    } else if (totalCount == 0 && page > 1) {
      throw new InvalidPageException("データが存在しません。");
    }

    int offset = (page - 1) * size;
    List<Task> tasks = taskRepository.findByPage(size, offset);

    return new TaskPageResult(tasks, totalCount);
  }

  @Override
  public long getTotalCount() {
    return taskRepository.countAll();
  }

  @Override
  public Task getTaskById(Integer id) {
    return taskRepository.findById(id)
        .orElseThrow(() -> new TaskNotFoundException("指定されたタスクは存在しません。ID: " + id));
  }

  /**
   * タスクを保存します。
   * 保存前に指定されたカテゴリIDがマスタに存在するか検証します。
   */
  @Override
  public void saveTask(Task task) {
    // カテゴリの存在チェック（存在しない場合は CategoryNotFoundException が送出される）
    categoryService.getCategoryById(task.getCategoryId());
    taskRepository.save(task);
  }

  @Override
  public void deleteTask(Integer id) {
    taskRepository.deleteById(id);
  }
}