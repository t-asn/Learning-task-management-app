package com.example.service;

import com.example.model.Task;
import java.util.List;

/**
 * タスク管理のビジネスロジックを定義するインターフェース。
 */
public interface TaskService {

  List<Task> getTasksByPage(int page, int size);

  long getTotalCount();

  Task getTaskById(Integer id);

  void saveTask(Task task);

  void deleteTask(Integer id);
}