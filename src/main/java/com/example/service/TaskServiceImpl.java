package com.example.service;

import com.example.exception.InvalidPageException;
import com.example.exception.TaskNotFoundException;
import com.example.model.Task;
import com.example.model.TaskPageResult;
import com.example.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;

  public TaskServiceImpl(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  @Override
  public TaskPageResult getTasksByPage(int page, int size) {
    // 1. ここで1回だけカウントを実行
    long totalCount = taskRepository.countAll();
    int totalPages = (int) Math.ceil((double) totalCount / size);

    // 2. ページ番号のバリデーション
    if (totalCount > 0 && (page < 1 || page > totalPages)) {
      throw new InvalidPageException("page=" + page);
    } else if (totalCount == 0 && page > 1) {
      throw new InvalidPageException("データが存在しません。");
    }

    // 3. データ取得
    int offset = (page - 1) * size;
    List<Task> tasks = taskRepository.findByPage(size, offset);

    // 4. カウント結果とリストをセットにして返却
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

  @Override
  public void saveTask(Task task) {
    taskRepository.save(task);
  }

  @Override
  public void deleteTask(Integer id) {
    taskRepository.deleteById(id);
  }
}