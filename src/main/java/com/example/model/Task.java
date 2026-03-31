package com.example.model;

import java.time.LocalDate;

public class Task {

  private Integer id;
  private String title;
  private String category;
  private LocalDate dueDate;

  public Task() {
  }

  public Task(Integer id, String title, String category, LocalDate dueDate) {
    this.id = id;
    this.title = title;
    this.category = category;
    this.dueDate = dueDate;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }
}