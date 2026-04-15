package com.example.service;

import com.example.model.Task;
import com.example.model.TaskPageResult;

public interface TaskService {

  TaskPageResult getTasksByPage(int page, int size);

  long getTotalCount();

  Task getTaskById(Integer id);

  void saveTask(Task task);

  void deleteTask(Integer id);
}