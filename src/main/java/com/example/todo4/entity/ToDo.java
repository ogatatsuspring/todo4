package com.example.todo4.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ToDo {
  private Integer id;
  private String todo;
  private String detail;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
