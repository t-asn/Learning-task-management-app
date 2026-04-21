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
  @Transactional
  public void updateStatus(Integer taskId, TaskStatus newStatus) {
    Task task = taskDao.findByIdForUpdate(taskId)
        .orElseThrow(() -> new TaskNotFoundException("ID:" + taskId + "は見つかりません"));
    task.setStatus(newStatus);
    taskDao.save(task);
  }

  @Override
  @Transactional
  public void saveTask(Task task) {
    // カテゴリの存在チェック
    categoryService.getCategoryById(task.getCategoryId());

    if (task.getId() == null) {
      // 【新規登録時】ステータスが未設定ならデフォルト値をセットして非NULL制約違反を防ぐ
      if (task.getStatus() == null) {
        task.setStatus(TaskStatus.NOT_STARTED);
      }
    } else {
      // 【更新時】存在確認
      if (!taskDao.existsById(task.getId())) {
        throw new TaskNotFoundException("更新対象が見つかりません。ID: " + task.getId());
      }
    }

    // @Idアノテーションを付けたTaskを渡すことで、正しくINSERT/UPDATEが実行されます
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

  @Override
  @Transactional(readOnly = true)
  public Task getTaskById(Integer id) {
    return taskDao.findById(id)
        .orElseThrow(() -> new TaskNotFoundException("ID:" + id + "は見つかりません"));
  }
}