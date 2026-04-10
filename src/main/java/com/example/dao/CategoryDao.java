package com.example.dao;

import com.example.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JDBC を利用したカテゴリ用データアクセスインターフェース。
 */
@Repository
public interface CategoryDao extends CrudRepository<Category, Integer> {

}