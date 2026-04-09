package com.example.service;

import com.example.model.Task;
import java.util.List;

/**
 * タスク管理に関するビジネスロジックを定義するインターフェース。
 */
public interface TaskService {

  List<Task> getAllTasks();

  // ポイント：戻り値は Optional ではなく Task 直接にする（Serviceで例外を投げるため）
  Task getTaskById(Integer id);

  void saveTask(Task task);

  void deleteTask(Integer id);
}