package com.example.repository;

import com.example.dao.TaskDao;
import com.example.exception.TaskNotFoundException;
import com.example.model.Task;
import com.example.model.TaskWithCategoryRow;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

  private final TaskDao taskDao;

  public TaskRepositoryImpl(TaskDao taskDao) {
    this.taskDao = taskDao;
  }

  @Override
  public List<TaskWithCategoryRow> findByPageWithCategory(int limit, int offset) {
    return taskDao.findByPageWithCategory(limit, offset);
  }

  @Override
  public long countAll() {
    return taskDao.countAll();
  }

  @Override
  public Optional<Task> findById(Integer id) {
    return taskDao.findById(id);
  }

  @Override
  public void save(Task task) {
    if (task.getId() != null && !taskDao.existsById(task.getId())) {
      throw new TaskNotFoundException("更新対象が見つかりません。ID: " + task.getId());
    }
    taskDao.save(task);
  }

  @Override
  public void deleteById(Integer id) {
    if (!taskDao.existsById(id)) {
      throw new TaskNotFoundException("削除対象が見つかりません。ID: " + id);
    }
    taskDao.deleteById(id);
  }
}