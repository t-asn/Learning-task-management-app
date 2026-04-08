package com.example.service;

import com.example.exception.InvalidPageException;
import com.example.exception.TaskNotFoundException;
import com.example.model.Task;
import com.example.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * TaskService の実装クラス。 ページネーションの計算など、ビジネス要件に基づく制御を行います。
 */
@Service
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;

  public TaskServiceImpl(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  /**
   * ページ番号と表示件数に基づき、バリデーション済みのタスクリストを取得します。
   *
   * @param page 現在のページ番号
   * @param size 1ページあたりの表示件数
   * @return 該当ページのタスクリスト
   */
  @Override
  public List<Task> getTasksByPage(int page, int size) {
    long totalCount = taskRepository.countAll();
    int totalPages = (int) Math.ceil((double) totalCount / size);

    // ページ番号の異常系バリデーション
    if (totalCount > 0 && (page < 1 || page > totalPages)) {
      throw new InvalidPageException("page=" + page);
    } else if (totalCount == 0 && page > 1) {
      throw new InvalidPageException("データが存在しません。");
    }

    int offset = (page - 1) * size;
    return taskRepository.findByPage(size, offset);
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

  @Override
  public void saveTask(Task task) {
    taskRepository.save(task);
  }

  @Override
  public void deleteTask(Integer id) {
    taskRepository.deleteById(id);
  }
}