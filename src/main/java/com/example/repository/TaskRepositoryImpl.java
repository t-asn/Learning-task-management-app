package com.example.repository;

import com.example.dao.TaskDao;
import com.example.exception.TaskNotFoundException;
import com.example.model.Task;
import com.example.model.TaskWithCategoryRow;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * {@link TaskRepository} の実装。
 * DBアクセスには Spring Data JDBC の {@link TaskDao} を利用します。
 */
@Repository
public class TaskRepositoryImpl implements TaskRepository {

  private final TaskDao taskDao;

  /**
   * @param taskDao タスク用DAO
   */
  public TaskRepositoryImpl(TaskDao taskDao) {
    this.taskDao = taskDao;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<TaskWithCategoryRow> findByPageWithCategory(int limit, int offset) {
    return taskDao.findByPageWithCategory(limit, offset);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long countAll() {
    return taskDao.countAll();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<Task> findById(Integer id) {
    return taskDao.findById(id);
  }

  /**
   * {@inheritDoc}
   *
   * <p>更新（IDあり）の場合、指定IDが存在しなければ例外とします。</p>
   *
   * @throws TaskNotFoundException 更新対象が存在しない場合
   */
  @Override
  public void save(Task task) {
    if (task.getId() != null && !taskDao.existsById(task.getId())) {
      throw new TaskNotFoundException("更新対象が見つかりません。ID: " + task.getId());
    }
    taskDao.save(task);
  }

  /**
   * {@inheritDoc}
   *
   * <p>削除対象が存在しなければ例外とします。</p>
   *
   * @throws TaskNotFoundException 削除対象が存在しない場合
   */
  @Override
  public void deleteById(Integer id) {
    if (!taskDao.existsById(id)) {
      throw new TaskNotFoundException("削除対象が見つかりません。ID: " + id);
    }
    taskDao.deleteById(id);
  }
}
