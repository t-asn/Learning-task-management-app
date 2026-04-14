package com.example.repository;

import com.example.model.Task;
import java.util.List;
import java.util.Optional;

/**
 * タスクデータアクセスの抽象メソッドを定義するインターフェース。
 */
public interface TaskRepository {

  List<Task> findByPage(int limit, int offset);

  long countAll();

  Optional<Task> findById(Integer id);

  void save(Task task);

  void deleteById(Integer id);
}