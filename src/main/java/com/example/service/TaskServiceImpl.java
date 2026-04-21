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
 * タスク管理のビジネスロジックを実装するクラス。
 */
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final TaskDao taskDao;
  private final CategoryService categoryService;

  @Override
  @Transactional(readOnly = true)
  public TaskPageResult getTasksByPage(int page, int size) {
    long totalCount = taskDao.countAll();
    int offset = (page - 1) * size;
    List<TaskWithCategoryRow> tasks = taskDao.findByPageWithCategory(size, offset);
    return new TaskPageResult(tasks, totalCount);
  }

  @Override
  @Transactional(readOnly = true)
  public Task getTaskById(Integer id) {
    return taskDao.findById(id)
        .orElseThrow(() -> new TaskNotFoundException("ID:" + id + "は見つかりません"));
  }

  /**
   * 【追加パーツ】カテゴリIDに紐づくタスク一覧を取得します。
   * カテゴリ詳細画面で使用されます。
   */
  @Override
  @Transactional(readOnly = true)
  public List<Task> getTasksByCategoryId(Integer categoryId) {
    return taskDao.findByCategoryId(categoryId);
  }

  @Override
  @Transactional
  public void saveTask(Task task) {
    categoryService.getCategoryById(task.getCategoryId());

    if (task.getId() == null) {
      if (task.getStatus() == null) {
        task.setStatus(TaskStatus.NOT_STARTED);
      }
    } else {
      if (!taskDao.existsById(task.getId())) {
        throw new TaskNotFoundException("更新対象が見つかりません。ID: " + task.getId());
      }
    }
    taskDao.save(task);
  }

  @Override
  @Transactional
  public void updateStatus(Integer taskId, TaskStatus newStatus) {
    Task task = taskDao.findByIdForUpdate(taskId)
        .orElseThrow(() -> new TaskNotFoundException("ID:" + taskId + "は見つかりません"));
    task.setStatus(newStatus);
    taskDao.save(task);
  }

  @Override
  @Transactional
  public void deleteTask(Integer id) {
    if (!taskDao.existsById(id)) {
      throw new TaskNotFoundException("削除対象が見つかりません。ID: " + id);
    }
    taskDao.deleteById(id);
  }
}