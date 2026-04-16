package com.example.service;

import com.example.dao.TaskDao;
import com.example.exception.InvalidPageException;
import com.example.exception.TaskNotFoundException;
import com.example.model.Task;
import com.example.model.TaskPageResult;
import com.example.model.TaskStatus;
import com.example.model.TaskWithCategoryRow;
import com.example.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * タスク管理業務ロジックの実装クラス。 ページネーション、悲観ロックを用いたステータス更新などを制御します。
 */
@Service
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;
  private final TaskDao taskDao;

  /**
   * コンストラクタ注入。 悲観ロック(FOR UPDATE)を直接実行するために TaskDao も注入します。
   */
  public TaskServiceImpl(TaskRepository taskRepository, TaskDao taskDao) {
    this.taskRepository = taskRepository;
    this.taskDao = taskDao;
  }

  /**
   * ページネーション対応のタスク一覧取得。 N+1問題を回避した TaskWithCategoryRow のリストを返します。
   */
  @Override
  @Transactional(readOnly = true)
  public TaskPageResult getTasksByPage(int page, int size) {
    long totalCount = taskRepository.countAll();
    int totalPages = (int) Math.ceil((double) totalCount / size);

    if (totalCount > 0 && (page < 1 || page > totalPages)) {
      throw new InvalidPageException("ページ番号が不正です: page=" + page);
    }

    int offset = (page - 1) * size;
    List<TaskWithCategoryRow> tasks = taskRepository.findByPageWithCategory(size, offset);

    return new TaskPageResult(tasks, totalCount);
  }

  /**
   * 悲観ロックを用いたステータス更新処理。
   *
   * @Transactional により、ロック取得から保存までを同一トランザクションで行います。
   */
  @Override
  @Transactional
  public void updateStatus(Integer taskId, String newStatus) {
    TaskStatus statusEnum;
    try {
      statusEnum = TaskStatus.valueOf(newStatus);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("不正なステータスです：status=" + newStatus);
    }

    Task task = taskDao.findByIdForUpdate(taskId)
        .orElseThrow(() -> new TaskNotFoundException("存在しないタスクです：taskId=" + taskId));

    task.changeStatus(statusEnum);
    taskDao.save(task);
  }

  @Override
  @Transactional(readOnly = true)
  public long getTotalCount() {
    return taskRepository.countAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Task getTaskById(Integer id) {
    return taskRepository.findById(id)
        .orElseThrow(() -> new TaskNotFoundException("指定されたタスクが見つかりません。ID: " + id));
  }

  @Override
  @Transactional
  public void saveTask(Task task) {
    taskRepository.save(task);
  }

  @Override
  @Transactional
  public void deleteTask(Integer id) {
    taskRepository.deleteById(id);
  }
}