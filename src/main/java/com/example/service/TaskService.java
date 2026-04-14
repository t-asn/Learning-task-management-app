package com.example.service;

import com.example.model.Task;
import com.example.model.TaskPageResult;
import java.util.List;

/**
 * タスク管理のビジネスロジックを定義するインターフェース。
 */
public interface TaskService {

  /**
   * ページ番号と表示件数に基づき、結果をカプセル化したオブジェクトを取得します。
   *
   * @param page 現在のページ番号
   * @param size 1ページあたりの表示件数
   * @return タスクリストと総件数を含むTaskPageResult
   */
  TaskPageResult getTasksByPage(int page, int size);

  long getTotalCount();

  Task getTaskById(Integer id);

  void saveTask(Task task);

  void deleteTask(Integer id);
}