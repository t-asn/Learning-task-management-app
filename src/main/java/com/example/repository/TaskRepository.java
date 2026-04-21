package com.example.repository;

import com.example.model.Task;
import com.example.model.TaskWithCategoryRow;

import java.util.List;
import java.util.Optional;

/**
 * タスクに関する永続化操作を抽象化するリポジトリ。
 * 実装（DBアクセスの詳細）は {@link com.example.repository.TaskRepositoryImpl} に隠蔽します。
 */
public interface TaskRepository {

  /**
   * 指定範囲のタスク一覧を取得します（カテゴリ名JOIN済み）。
   *
   * @param limit 取得件数
   * @param offset 開始位置（0始まり）
   * @return JOIN済みの行データ一覧
   */
  List<TaskWithCategoryRow> findByPageWithCategory(int limit, int offset);

  /**
   * タスクの総件数を取得します。
   *
   * @return 総件数
   */
  long countAll();

  /**
   * 指定IDのタスクを取得します。
   *
   * @param id タスクID
   * @return タスク（存在しない場合は空）
   */
  Optional<Task> findById(Integer id);

  /**
   * タスクを保存します（新規作成/更新）。
   *
   * @param task 保存対象のタスク
   */
  void save(Task task);

  /**
   * 指定IDのタスクを削除します。
   *
   * @param id 削除対象のタスクID
   */
  void deleteById(Integer id);
}
