package com.example.service;

import com.example.dao.TaskDao;
import com.example.exception.TaskNotFoundException;
import com.example.model.Task;
import com.example.model.TaskPageResult;
import com.example.model.TaskStatus;
import com.example.model.TaskWithCategoryRow;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * タスクに関するユースケース（一覧取得、登録、削除、ステータス更新など）を提供するサービス実装。
 */
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final TaskDao taskDao;

  /**
   * 指定ページのタスク一覧（カテゴリ名JOIN済み）と総件数を取得します。
   *
   * @param page 1始まりのページ番号
   * @param size 1ページあたりの件数
   * @return ページング結果（一覧と総件数）
   */
  @Override
  @Transactional(readOnly = true)
  public TaskPageResult getTasksByPage(int page, int size) {
    long totalCount = taskDao.countAll();
    int offset = (page - 1) * size;
    List<TaskWithCategoryRow> tasks = taskDao.findByPageWithCategory(size, offset);
    return new TaskPageResult(tasks, totalCount);
  }

  /**
   * タスクのステータスを更新します。
   *
   * @param taskId 更新対象のタスクID
   * @param newStatus 更新後のステータス
   * @throws TaskNotFoundException 指定IDのタスクが存在しない場合
   */
  @Override
  @Transactional
  public void updateStatus(Integer taskId, TaskStatus newStatus) {
    Task task = taskDao.findByIdForUpdate(taskId)
        .orElseThrow(() -> new TaskNotFoundException("ID:" + taskId + "は見つかりません"));
    task.setStatus(newStatus);
    taskDao.save(task);
  }

  /**
   * タスクを保存します（新規作成/更新）。
   *
   * @param task 保存対象のタスク
   */
  @Override
  @Transactional
  public void saveTask(Task task) {
    taskDao.save(task);
  }

  /**
   * 指定IDのタスクを削除します。
   *
   * @param id 削除対象のタスクID
   */
  @Override
  @Transactional
  public void deleteTask(Integer id) {
    taskDao.deleteById(id);
  }

  /**
   * 指定IDのタスクを取得します。
   *
   * @param id タスクID
   * @return タスクエンティティ
   * @throws TaskNotFoundException 指定IDのタスクが存在しない場合
   */
  @Override
  @Transactional(readOnly = true)
  public Task getTaskById(Integer id) {
    return taskDao.findById(id)
        .orElseThrow(() -> new TaskNotFoundException("ID:" + id + "は見つかりません"));
  }

  /**
   * タスクの総件数を取得します。
   *
   * @return 総件数
   */
  @Override
  @Transactional(readOnly = true)
  public long getTotalCount() {
    return taskDao.countAll();
  }
}
