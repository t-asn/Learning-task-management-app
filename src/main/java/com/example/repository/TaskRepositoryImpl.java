package com.example.repository;

import com.example.dao.TaskDao;
import com.example.exception.TaskNotFoundException;
import com.example.model.Task;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * TaskRepository インターフェースの実装クラス。 データの永続化において、存在確認を伴う安全な更新処理を提供します。
 */
@Repository
public class TaskRepositoryImpl implements TaskRepository {

  private final TaskDao taskDao;

  public TaskRepositoryImpl(TaskDao taskDao) {
    this.taskDao = taskDao;
  }

  @Override
  public List<Task> findAll() {
    return (List<Task>) taskDao.findAll();
  }

  @Override
  public Optional<Task> findById(Integer id) {
    return taskDao.findById(id);
  }

  /**
   * タスクを保存します。 更新（IDが存在する場合）の際は、事前にDBからレコードを取得し、 存在することを確認してから更新後のインスタンスで上書き保存を行います。
   *
   * @param task 保存・更新対象のタスク
   * @throws TaskNotFoundException 更新対象が存在しない場合
   */
  @Override
  public void save(Task task) {
    if (task.getId() != null) {
      taskDao.findById(task.getId())
          .orElseThrow(() -> new TaskNotFoundException(
              "更新対象のタスクが見つかりません。taskId=" + task.getId()));

      taskDao.save(task);
    } else {
      // 新規登録の場合はそのまま保存
      taskDao.save(task);
    }
  }

  @Override
  public void deleteById(Integer id) {
    if (!taskDao.existsById(id)) {
      throw new TaskNotFoundException("削除対象のタスクが見つかりません。taskId=" + id);
    }
    taskDao.deleteById(id);
  }
}