package com.example.repository;

import com.example.dao.TaskDao;
import com.example.exception.TaskNotFoundException;
import com.example.model.Task;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * TaskRepositoryの実装クラス。 データの永続化において、存在確認を伴う安全な更新処理を提供します。
 */
@Repository
public class TaskRepositoryImpl implements TaskRepository {

  private final TaskDao taskDao;

  /**
   * コンストラクタによる依存性の注入を行います。
   *
   * @param taskDao データアクセスオブジェクト
   */
  public TaskRepositoryImpl(TaskDao taskDao) {
    this.taskDao = taskDao;
  }

  /**
   * すべてのタスクを取得します。 強制キャストを排除し、DAOの派生クエリメソッドを利用して安全にListを取得します。
   *
   * @return タスクのリスト（ID降順）
   */
  @Override
  public List<Task> findByPage(int limit, int offset) {
    return taskDao.findByPage(limit, offset);
  }

  @Override
  public long countAll() {
    return taskDao.countAll();
  }

  /**
   * 指定されたIDのタスクを取得します。
   *
   * @param id タスクID
   * @return タスクを保持するOptional
   */
  @Override
  public Optional<Task> findById(Integer id) {
    return taskDao.findById(id);
  }

  /**
   * タスクを保存します。 IDが存在する場合はDBからレコードを取得し、存在確認をしてから更新します。
   *
   * @param task 保存対象のタスク
   */
  @Override
  public void save(Task task) {
    if (task.getId() != null) {
      taskDao.findById(task.getId())
          .orElseThrow(
              () -> new TaskNotFoundException("更新対象が見つかりません。ID: " + task.getId()));
    }
    taskDao.save(task);
  }

  /**
   * 指定されたIDのタスクを削除します。 事前に存在確認を行い、データが存在しない場合は例外を送出します。
   *
   * @param id 削除対象のタスクID
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