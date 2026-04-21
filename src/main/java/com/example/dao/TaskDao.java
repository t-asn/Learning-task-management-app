package com.example.dao;

import com.example.model.Task;
import com.example.model.TaskWithCategoryRow;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskDao extends CrudRepository<Task, Integer> {

  @Query("""
    SELECT t.*, c.name as category_name 
    FROM tasks t 
    JOIN categories c ON t.category_id = c.id 
    ORDER BY t.id DESC 
    LIMIT :size OFFSET :offset
    """)
  List<TaskWithCategoryRow> findByPageWithCategory(int size, int offset);

  @Query("SELECT * FROM tasks WHERE id = :id FOR UPDATE")
  Optional<Task> findByIdForUpdate(@Param("id") Integer id);

  @Query("SELECT COUNT(*) FROM tasks")
  long countAll();

  @Query("SELECT * FROM tasks WHERE category_id = :categoryId ORDER BY due_date ASC")
  List<Task> findByCategoryId(Integer categoryId);
}
