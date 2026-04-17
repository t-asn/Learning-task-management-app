package com.example.service;

import com.example.exception.InvalidPageException;
import com.example.exception.TaskNotFoundException;
import com.example.model.Task;
import com.example.model.TaskPageResult;
import com.example.model.TaskWithCategoryRow;
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
    long totalCount = taskRepository.countAll();
    int totalPages = (int) Math.ceil((double) totalCount / size);

    if (totalCount > 0 && (page < 1 || page > totalPages)) {
      throw new InvalidPageException("page=" + page);
    }

    int offset = (page - 1) * size;
    List<TaskWithCategoryRow> tasks = taskRepository.findByPageWithCategory(size, offset);
    return new TaskPageResult(tasks, totalCount);
  }

  @Override
  public long getTotalCount() {
    return taskRepository.countAll();
  }

  @Override
  public Task getTaskById(Integer id) {
    return taskRepository.findById(id)
        .orElseThrow(() -> new TaskNotFoundException("指定されたタスクが見つかりません。ID: " + id));
  }

  @Override
  public void saveTask(Task task) {
    taskRepository.save(task);
  }

  @Override
  public void deleteTask(Integer id) {
    taskRepository.deleteById(id);
  }

  @Override
  public void updateStatus(Integer taskId, String newStatus) {

  }
}