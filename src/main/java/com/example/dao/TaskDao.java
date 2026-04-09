package com.example.dao;

import com.example.model.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Spring Data JDBC を利用したデータアクセス用インターフェース。
 */
@Repository
public interface TaskDao extends CrudRepository<Task, Integer> {

  /**
   * タスクをIDの降順で全件取得します。
   *
   * @return ID降順にソートされたタスクのリスト
   */
  List<Task> findAllByOrderByIdDesc();

}