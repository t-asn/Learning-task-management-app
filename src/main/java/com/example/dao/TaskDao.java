package com.example.dao;

import com.example.model.Task;
import com.example.model.TaskWithCategoryRow;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskDao extends CrudRepository<Task, Integer> {

  @Query("""
      SELECT t.id, t.title, t.category_id, c.name AS category_name, t.due_date 
      FROM tasks t 
      INNER JOIN categories c ON t.category_id = c.id 
      ORDER BY t.id DESC LIMIT :limit OFFSET :offset
      """)
  List<TaskWithCategoryRow> findByPageWithCategory(@Param("limit") int limit, @Param("offset") int offset);

  @Query("SELECT COUNT(*) FROM tasks")
  long countAll();
}