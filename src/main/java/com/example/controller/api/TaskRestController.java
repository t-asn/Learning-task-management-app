package com.example.controller.api;

import com.example.controller.api.dto.TaskPageResponse;
import com.example.controller.api.dto.TaskRequest;
import com.example.controller.api.dto.TaskResponse;
import com.example.model.Task;
import com.example.service.CategoryService;
import com.example.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * タスク REST API（JSON）。
 */
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskRestController {

  private final TaskService taskService;
  private final CategoryService categoryService;

  @GetMapping
  public TaskPageResponse list(
      @RequestParam(defaultValue = "1") @Min(1) int page,
      @RequestParam(defaultValue = "5") @Min(1) int size) {
    var result = taskService.getTasksByPage(page, size);

    List<TaskResponse> tasks = result.tasks().stream()
        .map(TaskResponse::fromRow)
        .collect(Collectors.toList());

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
    Task task = request.toModelForCreate();
    taskService.saveTask(task);

    var category = categoryService.getCategoryById(task.getCategoryId());
    TaskResponse body = TaskResponse.fromTask(task, category.getName());
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(task.getId())
        .toUri();
    return ResponseEntity.created(location).body(body);
  }

  @PutMapping("/{id}")
  public TaskResponse update(@PathVariable Integer id, @Valid @RequestBody TaskRequest request) {
    Task task = request.toModelForUpdate(id);
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
