package com.example.service;

import com.example.dao.TaskDao;
import com.example.exception.TaskNotFoundException;
import com.example.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
  private final TaskDao taskDao;

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
    // 悲観ロック付きで取得
    Task task = taskDao.findByIdForUpdate(taskId)
        .orElseThrow(() -> new TaskNotFoundException("ID:" + taskId + "は見つかりません"));
    task.setStatus(newStatus);
    taskDao.save(task);
  }

  @Override
  @Transactional
  public void saveTask(Task task) { taskDao.save(task); }

  @Override
  @Transactional
  public void deleteTask(Integer id) { taskDao.deleteById(id); }

  @Override
  @Transactional(readOnly = true)
  public Task getTaskById(Integer id) {
    return taskDao.findById(id).orElseThrow(() -> new TaskNotFoundException("ID:" + id + "は見つかりません"));
  }

  @Override
  @Transactional(readOnly = true)
  public long getTotalCount() { return taskDao.countAll(); }
}