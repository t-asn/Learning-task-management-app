package com.example.service;

import com.example.model.Task;
import com.example.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {

  private final TaskRepository taskRepository;

  public TaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public List<Task> getAllTasks() {
    return taskRepository.findAll();
  }

  public Task getTaskById(Integer id) {
    return taskRepository.findById(id).orElse(null);
  }

  public void saveTask(Task task) {
    taskRepository.save(task);
  }

  public void deleteTask(Integer id) {
    taskRepository.deleteById(id);
  }
}