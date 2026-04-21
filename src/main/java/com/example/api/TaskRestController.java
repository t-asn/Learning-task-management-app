package com.example.api;

import com.example.api.dto.TaskPageResponse;
import com.example.api.dto.TaskRequest;
import com.example.api.dto.TaskResponse;
import com.example.model.Task;
import com.example.service.CategoryService;
import com.example.service.TaskService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * タスク REST API（JSON）。
 */
@RestController
@RequestMapping("/api/tasks")
@Validated
@RequiredArgsConstructor
public class TaskRestController {

  private final TaskService taskService;
  private final CategoryService categoryService;

  private static Task toTask(Integer id, TaskRequest request) {
    Task t = new Task();
    t.setId(id);
    t.setTitle(request.title());
    t.setCategoryId(request.categoryId());
    t.setDueDate(request.dueDate());
    t.setStatus(request.status());
    return t;
  }

  @GetMapping
  public TaskPageResponse list(
      @RequestParam(defaultValue = "1") @Min(1) int page,
      @RequestParam(defaultValue = "5") @Min(1) int size) {
    var result = taskService.getTasksByPage(page, size);
    var tasks = result.tasks().stream()
        .map(TaskResponse::fromRow)
        .toList();
    return new TaskPageResponse(tasks, result.totalCount(), page, size);
  }

  @GetMapping("/{id}")
  public TaskResponse get(@PathVariable Integer id) {
    Task task = taskService.getTaskById(id);
    var category = categoryService.getCategoryById(task.getCategoryId());
    return TaskResponse.fromTask(task, category.getName());
  }

  @PostMapping
  public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskRequest request) {
    Task task = toTask(null, request);
    taskService.saveTask(task);
    var category = categoryService.getCategoryById(task.getCategoryId());
    var body = TaskResponse.fromTask(task, category.getName());
    var location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(task.getId())
        .toUri();
    return ResponseEntity.created(location).body(body);
  }

  @PutMapping("/{id}")
  public TaskResponse update(@PathVariable Integer id, @Valid @RequestBody TaskRequest request) {
    Task task = toTask(id, request);
    taskService.saveTask(task);
    var category = categoryService.getCategoryById(task.getCategoryId());
    return TaskResponse.fromTask(task, category.getName());
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Integer id) {
    taskService.deleteTask(id);
  }
}
