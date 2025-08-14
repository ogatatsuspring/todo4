package com.example.todo4.helper;

import com.example.todo4.entity.ToDo;
import com.example.todo4.form.ToDoForm;

public class ToDoHelper {
  
  public static ToDo convertToDo( ToDoForm form ) {
    ToDo todo = new ToDo();
    todo.setId( form.getId() );
    todo.setTodo( form.getTodo() );
    todo.setDetail( form.getDetail() );
    return todo;
  }
  
  public static ToDoForm convertToDoForm( ToDo todo ) {
    ToDoForm form = new ToDoForm();
    form.setId( todo.getId() );
    form.setTodo( todo.getTodo() );
    form.setDetail( todo.getDetail() );
    form.setIsNew( false );
    return form;
  }

}
