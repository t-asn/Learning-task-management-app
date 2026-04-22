package com.example.controller.api;

import com.example.controller.api.dto.TaskPageResponse;
import com.example.controller.api.dto.TaskRequest;
import com.example.controller.api.dto.TaskResponse;
import com.example.model.Task;
import com.example.model.TaskPageResult;
import com.example.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Task 関連の API DTO とモデルの相互変換を行う Mapper。
 */
@Component
@RequiredArgsConstructor
public class TaskApiMapper {

  private final CategoryService categoryService;

  /**
   * TaskRequest から Task モデルへ変換します。
   */
  public Task toModel(Integer id, TaskRequest request) {
    Task t = new Task();
    t.setId(id);
    t.setTitle(request.title());
    t.setCategoryId(request.categoryId());
    t.setDueDate(request.dueDate());
    t.setStatus(request.status());
    return t;
  }

  /**
   * Task モデルから TaskResponse DTO へ変換します。
   */
  public TaskResponse toResponse(Task task) {
    var category = categoryService.getCategoryById(task.getCategoryId());
    return TaskResponse.fromTask(task, category.getName());
  }

  /**
   * TaskPageResult から TaskPageResponse DTO へ変換します。
   */
  public TaskPageResponse toPageResponse(TaskPageResult result, int page, int size) {
    List<TaskResponse> tasks = result.tasks().stream()
        .map(TaskResponse::fromRow)
        .toList();
    return new TaskPageResponse(tasks, result.totalCount(), page, size);
  }
}
