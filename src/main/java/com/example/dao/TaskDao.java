package com.example.dao;

import com.example.model.Task;
import com.example.model.TaskWithCategoryRow;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JDBC を利用したタスク用データアクセスインターフェース（DAO）。
 */
@Repository
public interface TaskDao extends CrudRepository<Task, Integer> {

  /**
   * タスク一覧をカテゴリ名とともに取得します。
   *
   * <p>カテゴリID（外部キー）に対して categories と JOIN し、画面表示に必要な情報を1回のクエリで取得します。</p>
   *
   * @param limit 取得件数
   * @param offset 開始位置（0始まり）
   * @return JOIN済みの行データ一覧
   */
  @Query("""
        SELECT t.id, t.title, t.category_id, c.name AS category_name, t.due_date, t.status
        FROM tasks t INNER JOIN categories c ON t.category_id = c.id
        ORDER BY t.id ASC LIMIT :limit OFFSET :offset
        """)
  List<TaskWithCategoryRow> findByPageWithCategory(@Param("limit") int limit, @Param("offset") int offset);

  /**
   * 指定IDのタスクを行ロック（悲観ロック）付きで取得します。
   *
   * @param id タスクID
   * @return タスク（存在しない場合は空）
   */
  @Query("SELECT * FROM tasks WHERE id = :id FOR UPDATE")
  Optional<Task> findByIdForUpdate(@Param("id") Integer id);

  /**
   * タスクの総件数を取得します。
   *
   * @return 総件数
   */
  @Query("SELECT COUNT(*) FROM tasks")
  long countAll();
}
