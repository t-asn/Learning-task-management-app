package com.example.repository;

import com.example.model.Task;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryTaskRepository implements TaskRepository {

  private final List<Task> tasks = new ArrayList<>();
  private final AtomicInteger idGenerator = new AtomicInteger(1);

  @Override
  public List<Task> findAll() {
    return new ArrayList<>(tasks);
  }

  @Override
  public Optional<Task> findById(Integer id) {
    return tasks.stream().filter(t -> t.getId().equals(id)).findFirst();
  }

  @Override
  public void save(Task task) {
    if (task.getId() == null) {
      task.setId(idGenerator.getAndIncrement());
      tasks.add(task);
    } else {
      findById(task.getId()).ifPresent(existingTask -> {
        existingTask.setTitle(task.getTitle());
        existingTask.setCategory(task.getCategory());
        existingTask.setDueDate(task.getDueDate());
      });
    }
  }

  @Override
  public void deleteById(Integer id) {
    tasks.removeIf(t -> t.getId().equals(id));
  }
}