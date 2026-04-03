package com.example.repository;

import com.example.dao.TaskDao;
import com.example.model.Task;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * TaskRepository インターフェースの実装クラス。 Data Access Object (TaskDao) をラップし、ドメイン層に対して適切な型でデータを提供します。 直接 Dao
 * を呼ばず本クラスを経由することで、将来的な永続化先の変更やロジックの追加を局所化します。
 */
@Repository
public class TaskRepositoryImpl implements TaskRepository {

  private final TaskDao taskDao;

  /**
   * コンストラクタ。 Springにより TaskDao (CrudRepository) がインジェクションされます。
   *
   * @param taskDao データアクセス用インターフェース
   */
  public TaskRepositoryImpl(TaskDao taskDao) {
    this.taskDao = taskDao;
  }

  /**
   * 全てのタスクを取得します。 CrudRepository の戻り値 (Iterable) を List に変換して返します。
   *
   * @return 全タスクのリスト
   */
  @Override
  public List<Task> findAll() {
    return ((List<Task>) taskDao.findAll());
  }

  /**
   * 指定されたIDのタスクを取得します。
   *
   * @param id タスクID
   * @return タスクを保持する Optional
   */
  @Override
  public Optional<Task> findById(Integer id) {
    return taskDao.findById(id);
  }

  /**
   * タスクを保存（新規登録または更新）します。
   *
   * @param task 保存対象のタスクオブジェクト
   */
  @Override
  public void save(Task task) {
    taskDao.save(task);
  }

  /**
   * 指定されたIDのタスクを物理削除します。
   *
   * @param id 削除対象のタスクID
   */
  @Override
  public void deleteById(Integer id) {
    taskDao.deleteById(id);
  }
}