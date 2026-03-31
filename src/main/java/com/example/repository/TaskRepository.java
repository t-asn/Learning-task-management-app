package com.example.repository;

import com.example.model.Task;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {

  List<Task> findAll();

  Optional<Task> findById(Integer id);

  void save(Task task);

  void deleteById(Integer id);
}