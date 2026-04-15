package com.example.dao;

import com.example.model.Task;
import com.example.model.TaskWithCategoryRow;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Taskテーブルへのデータアクセスを管理するリポジトリ。
 * 標準的なCRUD操作に加え、JOINを用いた効率的なデータ取得を定義します。
 */
@Repository
public interface TaskDao extends CrudRepository<Task, Integer> {

  /**
   * カテゴリ名をJOINして、ページングされたタスク一覧を取得します。
   *
   * @param limit  取得件数
   * @param offset 読み飛ばし件数
   * @return JOIN済みの結果を格納したTaskWithCategoryRowのリスト
   */
  @Query("""
      SELECT 
        t.id, 
        t.title, 
        t.category_id, 
        c.name AS category_name, 
        t.due_date 
      FROM tasks t 
      INNER JOIN categories c ON t.category_id = c.id 
      ORDER BY t.id DESC 
      LIMIT :limit OFFSET :offset
      """)
  List<TaskWithCategoryRow> findByPageWithCategory(@Param("limit") int limit, @Param("offset") int offset);

  /**
   * 特定のカテゴリに紐づくタスクをID降順で取得します。
   *
   * @param categoryId カテゴリID
   * @return 該当するタスクのリスト
   */
  @Query("SELECT * FROM tasks WHERE category_id = :categoryId ORDER BY id DESC")
  List<Task> findByCategoryId(@Param("categoryId") Integer categoryId);

  /**
   * タスクの総件数を取得します（ページネーション計算用）。
   *
   * @return 総件数
   */
  @Query("SELECT COUNT(*) FROM tasks")
  long countAll();

  /**
   * IDの降順で全てのタスクを取得します。
   *
   * @return タスクリスト
   */
  List<Task> findAllByOrderByIdDesc();
}