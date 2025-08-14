package com.example.todo4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.todo4.entity.ToDo;
import com.example.todo4.form.ToDoForm;
import com.example.todo4.helper.ToDoHelper;
import com.example.todo4.service.ToDoService;

@Controller
public class ToDoController {
  
  private final ToDoService toDoService;
  
  public ToDoController( ToDoService toDoService ) {
    this.toDoService = toDoService;
  }
  
  @GetMapping( "/todos" )
  public String list( Model model ) {
    model.addAttribute( "todos", toDoService.findAllToDo() );
    return "todo/list";
  }
  
  @GetMapping( "/todos/{id}" )
  public String detail( @PathVariable Integer id, Model model, RedirectAttributes attributes ) {
    ToDo toDo = toDoService.findByIdToDo( id );
    if ( toDo != null ) {
      model.addAttribute( "todo", toDo);
      return "todo/detail";
    } else {
      attributes.addFlashAttribute( "errorMessage", "対象データがありません" );
      return "redirect:/todos";
    }
  }
  
  @GetMapping( "/todos/form" )
  public String newToDo( @ModelAttribute ToDoForm form ) {
    form.setIsNew( true );
    return "todo/form";
  }
  
  @PostMapping( "/todos/save" )
  public String create( @Validated ToDoForm form, BindingResult bindingResult, RedirectAttributes attributes ) {
    
    if ( bindingResult.hasErrors() ) {
      form.setIsNew( true );
      return "todo/form";
    }
    
    ToDo todo = ToDoHelper.convertToDo( form );
    toDoService.insertToDo( todo );
    attributes.addFlashAttribute( "message", "新しいToDoが作成されました" );
    return "redirect:/todos";
  }
  
  @GetMapping( "/todos/edit/{id}" )
  public String edit( @PathVariable Integer id, Model model, RedirectAttributes attributes ) {
    ToDo target = toDoService.findByIdToDo( id );
    if ( target != null ) {
      ToDoForm form = ToDoHelper.convertToDoForm( target );
      model.addAttribute( "toDoForm", form );
      return "todo/form";
    } else {
      attributes.addFlashAttribute( "errorMessage", "対象データがありません" );
      return "redirect:/todos";
    }
  }
  
  @PostMapping( "/todos/update" )
  public String update( @Validated ToDoForm form, BindingResult bindingResult, RedirectAttributes attributes ) {
    
    if ( bindingResult.hasErrors() ) {
      form.setIsNew( false );
      return( "todo/form" );
    }
    
    ToDo todo = ToDoHelper.convertToDo( form );
    if ( toDoService.updateToDo( todo ) ) {
      attributes.addFlashAttribute( "message", "ToDoが更新されました" );
      return "redirect:/todos";
    } else {
      attributes.addFlashAttribute( "errorMessage", "ToDo更新が失敗しました" );
      return "redirect:/todos";
    }
  }
  
  @PostMapping( "/todos/delete/{id}")
  public String delete( @PathVariable Integer id, RedirectAttributes attributes ) {
    if ( toDoService.deleteToDo( id ) ) {
      attributes.addFlashAttribute( "message", "ToDoが削除されました" );
      return "redirect:/todos";
    } else {
      attributes.addFlashAttribute( "errorMessage", "ToDo削除が失敗しました" );
      return "redirect:/todos";
    }
  }

}
