package com.example.repository;

import com.example.model.Task;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * メモリ上でタスクデータを管理するリポジトリの実装クラス。 データベースの代わりに List を使用して、アプリケーション実行中のデータを保持します。
 */
@Repository
public class InMemoryTaskRepository implements TaskRepository {

  /**
   * タスクデータを格納するリスト
   */
  private final List<Task> tasks = new ArrayList<>();

  /**
   * タスクIDを自動採番するためのカウンタ
   */
  private final AtomicInteger idGenerator = new AtomicInteger(1);

  /**
   * 保持されているすべてのタスクを取得する。 呼び出し元での操作が元のリストに影響を与えないよう、新しいリストを作成して返します。
   *
   * @return タスクオブジェクトのリスト
   */
  @Override
  public List<Task> findAll() {
    return new ArrayList<>(tasks);
  }

  /**
   * 指定されたIDに一致するタスクを検索する。
   *
   * @param id 検索対象のタスクID
   * @return 見つかったタスクを含む Optional オブジェクト（見つからない場合は empty）
   */
  @Override
  public Optional<Task> findById(Integer id) {
    return tasks.stream().filter(t -> t.getId().equals(id)).findFirst();
  }

  /**
   * タスクを保存する。 IDが null の場合は新規登録として採番を行い、IDがある場合は既存データの更新を行います。
   *
   * @param task 保存対象のタスクオブジェクト
   */
  @Override
  public void save(Task task) {
    if (task.getId() == null) {
      // 新規登録：新しいIDを発行してリストに追加
      task.setId(idGenerator.getAndIncrement());
      tasks.add(task);
    } else {
      // 更新：既存のデータをIDで検索し、内容を書き換える
      findById(task.getId()).ifPresent(existingTask -> {
        existingTask.setTitle(task.getTitle());
        existingTask.setCategory(task.getCategory());
        existingTask.setDueDate(task.getDueDate());
      });
    }
  }

  /**
   * 指定されたIDのタスクをリストから削除する。
   *
   * @param id 削除対象のタスクID
   */
  @Override
  public void deleteById(Integer id) {
    tasks.removeIf(t -> t.getId().equals(id));
  }
}