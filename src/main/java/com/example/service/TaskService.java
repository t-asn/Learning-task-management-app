package com.example.service;

import com.example.model.Task;
import com.example.repository.TaskRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * タスク管理に関するビジネスロジックを処理するサービスクラス。
 * コントローラーからの要求を受け取り、リポジトリを利用してデータの参照・更新・削除を行います。
 */
@Service
public class TaskService {

  private final TaskRepository taskRepository;

  /**
   * コンストラクタ。
   * Springによって適切なTaskRepositoryの実装（InMemoryTaskRepository等）が注入されます。
   * @param taskRepository 使用するリポジトリ
   */
  public TaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  /**
   * すべてのタスクを取得する。
   * @return 登録されているタスクの全リスト
   */
  public List<Task> getAllTasks() {
    return taskRepository.findAll();
  }

  /**
   * IDを指定して特定のタスクを1件取得する。
   * @param id 取得対象のタスクID
   * @return 指定されたIDのタスク（存在しない場合は null）
   */
  public Optional<Task> getTaskById(Integer id) {
    return taskRepository.findById(id);
  }

  /**
   * タスクの保存処理を行う。
   * @param task 保存するタスクオブジェクト
   */
  public void saveTask(Task task) {
    taskRepository.save(task);
  }

  /**
   * 指定されたIDのタスクを削除する。
   * @param id 削除対象のタスクID
   */
  public void deleteTask(Integer id) {
    taskRepository.deleteById(id);
  }
}