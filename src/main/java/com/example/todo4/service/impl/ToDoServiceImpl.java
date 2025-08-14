package com.example.todo4.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.todo4.entity.ToDo;
import com.example.todo4.repository.ToDoDao;
import com.example.todo4.service.ToDoService;

@Service
@Transactional
public class ToDoServiceImpl implements ToDoService {
  
  private final ToDoDao toDoDao;
  
  public ToDoServiceImpl( ToDoDao toDoDao ) {
    this.toDoDao = toDoDao;
  }
  
  @Override
  public List<ToDo> findAllToDo() {
    return toDoDao.selectAll();
  }
  
  @Override
  public ToDo findByIdToDo( Integer id ) {
    return toDoDao.selectById( id );
  }
  
  @Override
  public boolean insertToDo( ToDo toDo ) {
    if ( toDoDao.insert( toDo ) == 1 ) {
      return true;
    } else {
      return false;
    }
  }
  
  @Override
  public boolean updateToDo( ToDo toDo ) {
    if ( toDoDao.update( toDo ) == 1 ) {
      return true;
    } else {
      return false;
    }
  }
  
  @Override
  public boolean deleteToDo( Integer id ) {
    if ( toDoDao.delete( id ) == 1 ) {
      return true;
    } else {
      return false;
    }
  }

}
