package com.example.service;

import com.example.model.Task;
import com.example.model.TaskPageResult;
import com.example.model.TaskStatus;

/**
 * タスクに関するユースケース（一覧取得、登録、削除、ステータス更新など）を定義するサービスインターフェース。
 */
public interface TaskService {

  /**
   * 指定ページのタスク一覧（カテゴリ名JOIN済み）と総件数を取得します。
   *
   * @param page 1始まりのページ番号
   * @param size 1ページあたりの件数
   * @return ページング結果（一覧と総件数）
   */
  TaskPageResult getTasksByPage(int page, int size);

  /**
   * タスクの総件数を取得します。
   *
   * @return 総件数
   */
  long getTotalCount();

  /**
   * 指定IDのタスクを取得します。
   *
   * @param id タスクID
   * @return タスクエンティティ
   */
  Task getTaskById(Integer id);

  /**
   * タスクを保存します（新規作成/更新）。
   *
   * @param task 保存対象のタスク
   */
  void saveTask(Task task);

  /**
   * 指定IDのタスクを削除します。
   *
   * @param id 削除対象のタスクID
   */
  void deleteTask(Integer id);

  /**
   * タスクのステータスを更新します。
   *
   * @param taskId 更新対象のタスクID
   * @param newStatus 更新後のステータス
   */
  void updateStatus(Integer taskId, TaskStatus newStatus);
}
