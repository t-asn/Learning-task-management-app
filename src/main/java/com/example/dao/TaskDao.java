package com.example.dao;

import com.example.model.Task;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskDao extends CrudRepository<Task, Integer> {

  @Query("SELECT * FROM tasks ORDER BY id DESC LIMIT :limit OFFSET :offset")
  List<Task> findByPage(@Param("limit") int limit, @Param("offset") int offset);

  @Query("SELECT COUNT(*) FROM tasks")
  long countAll();
}