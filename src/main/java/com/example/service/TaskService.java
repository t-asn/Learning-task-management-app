package com.example.service;

import com.example.model.Task;
import com.example.model.TaskPageResult;
import com.example.model.TaskStatus;

public interface TaskService {

  TaskPageResult getTasksByPage(int page, int size);
  long getTotalCount();
  Task getTaskById(Integer id);

  void saveTask(Task task);
  void deleteTask(Integer id);
  void updateStatus(Integer taskId, TaskStatus newStatus);
}
