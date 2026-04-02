package com.example.service;

import com.example.exception.TaskNotFoundException;
import com.example.model.Task;
import com.example.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * TaskServiceの実装クラス。
 */
@Service //
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;

  public TaskServiceImpl(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  @Override
  public List<Task> getAllTasks() {
    return taskRepository.findAll();
  }

  /**
   * RepositoryからOptionalで受け取り、中身がなければ自作例外をスローする。
   */
  @Override
  public Task getTaskById(Integer id) {
    return taskRepository.findById(id)
        .orElseThrow(
            () -> new TaskNotFoundException("指定されたタスクは存在しません：taskId=" + id));
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