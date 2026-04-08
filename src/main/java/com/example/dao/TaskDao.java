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
   * 派生クエリメソッドを使用することで、戻り値を直接 List 型として定義でき、呼び出し元での Iterable からの強制キャストを不要にします。
   *
   * @return ID降順にソートされたタスクのリスト
   */
  List<Task> findAllByOrderByIdDesc();

}