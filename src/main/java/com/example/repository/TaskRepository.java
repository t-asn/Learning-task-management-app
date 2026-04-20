package com.example.repository;

import com.example.model.Task;
import com.example.model.TaskWithCategoryRow;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

  List<TaskWithCategoryRow> findByPageWithCategory(int limit, int offset);
  long countAll();
  Optional<Task> findById(Integer id);

  void save(Task task);
  void deleteById(Integer id);
}
