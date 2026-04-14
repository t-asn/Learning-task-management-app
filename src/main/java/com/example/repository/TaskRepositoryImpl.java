package com.example.repository;

import com.example.dao.TaskDao;
import com.example.exception.TaskNotFoundException;
import com.example.model.Task;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * TaskRepositoryの実装クラス。
 * データの永続化において、存在確認を伴う安全な更新処理を提供します。
 */
@Repository
public class TaskRepositoryImpl implements TaskRepository {

  private final TaskDao taskDao;

  public TaskRepositoryImpl(TaskDao taskDao) {
    this.taskDao = taskDao;
  }

  @Override
  public List<Task> findAll() {
    return taskDao.findAllByOrderByIdDesc();
  }

  @Override
  public List<Task> findByPage(int limit, int offset) {
    return taskDao.findByPage(limit, offset);
  }

  @Override
  public long countAll() {
    return taskDao.countAll();
  }

  @Override
  public Optional<Task> findById(Integer id) {
    return taskDao.findById(id);
  }

  /**
   * タスクを保存します。
   * 更新時は事前にDBからレコードを取得し、存在確認をしてから上書きします。
   */
  @Override
  public void save(Task task) {
    if (task.getId() != null) {
      taskDao.findById(task.getId())
          .orElseThrow(() -> new TaskNotFoundException("更新対象が見つかりません。ID: " + task.getId()));
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