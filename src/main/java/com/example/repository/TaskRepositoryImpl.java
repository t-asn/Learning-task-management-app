package com.example.repository;

import com.example.dao.TaskDao;
import com.example.model.Task;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Repository
public class TaskRepositoryImpl implements TaskRepository {
  private final TaskDao taskDao;

  public TaskRepositoryImpl(TaskDao taskDao) {
    this.taskDao = taskDao;
  }

  @Override
  public List<Task> findAll() {
    return StreamSupport.stream(taskDao.findAll().spliterator(), false).toList();
  }

  @Override
  public Optional<Task> findById(Integer id) {
    return taskDao.findById(id);
  }

  @Override
  public void save(Task task) {
    taskDao.save(task);
  }

  @Override
  public void deleteById(Integer id) {
    taskDao.deleteById(id);
  }
}