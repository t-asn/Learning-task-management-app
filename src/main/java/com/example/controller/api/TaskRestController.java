package com.example.controller.api;

import com.example.controller.api.dto.TaskPageResponse;
import com.example.controller.api.dto.TaskRequest;
import com.example.controller.api.dto.TaskResponse;
import com.example.model.Task;
import com.example.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * タスク REST API（JSON）。
 */
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskRestController {

  private final TaskService taskService;
  private final TaskApiMapper mapper;

  @GetMapping
  public TaskPageResponse list(
      @RequestParam(defaultValue = "1") @Min(1) int page,
      @RequestParam(defaultValue = "5") @Min(1) int size) {
    var result = taskService.getTasksByPage(page, size);
    return mapper.toPageResponse(result, page, size);
  }

  @GetMapping("/{id}")
  public TaskResponse get(@PathVariable Integer id) {
    Task task = taskService.getTaskById(id);
    return mapper.toResponse(task);
  }

  @PostMapping
  public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskRequest request) {
    Task task = request.toModel(null);
    taskService.saveTask(task);

    TaskResponse body = mapper.toResponse(task);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(task.getId())
        .toUri();
    return ResponseEntity.created(location).body(body);
  }

  @PutMapping("/{id}")
  public TaskResponse update(@PathVariable Integer id, @Valid @RequestBody TaskRequest request) {
    Task task = request.toModel(id);
    taskService.saveTask(task);
    return mapper.toResponse(task);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Integer id) {
    taskService.deleteTask(id);
  }
}
