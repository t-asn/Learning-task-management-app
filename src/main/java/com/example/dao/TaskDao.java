package com.example.dao;

import com.example.model.Task;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Spring Data JDBC を利用したデータアクセス用インターフェース。
 */
@Repository
public interface TaskDao extends CrudRepository<Task, Integer> {

  /**
<<<<<<< HEAD
   * IDの降順で、指定された範囲のタスクを取得します。
   *
   * @param limit  取得件数 (1ページあたりの件数)
   * @param offset 読み飛ばす件数
   * @return タスクリスト
   */
  @Query("SELECT * FROM tasks ORDER BY id DESC LIMIT :limit OFFSET :offset")
  List<Task> findByPage(@Param("limit") int limit, @Param("offset") int offset);

  /**
   * 全タスクの総件数を取得します。
   *
   * @return 総件数
   */
  @Query("SELECT COUNT(*) FROM tasks")
  long countAll();
}