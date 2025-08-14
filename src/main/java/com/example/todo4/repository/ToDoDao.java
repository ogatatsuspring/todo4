package com.example.todo4.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.example.todo4.entity.ToDo;

@Repository
public class ToDoDao {
  
  private final DataSource dataSource;
  
  public ToDoDao( DataSource dataSource ) {
    this.dataSource = dataSource;
  }

  
  
  private ToDo mapRow( ResultSet rs ) throws SQLException {
    ToDo toDo = new ToDo();
    toDo.setId( rs.getInt( "id" ) );
    toDo.setTodo( rs.getString( "todo" ) );
    toDo.setDetail( rs.getString( "detail" ) );
    Timestamp cts = rs.getTimestamp( "createdAt" );
    Timestamp uts = rs.getTimestamp( "updatedAt" );
    toDo.setCreatedAt( cts != null ? cts.toLocalDateTime() : null );
    toDo.setUpdatedAt( uts != null ? cts.toLocalDateTime() : null );
    return toDo;
  }
  
  
  
  public List<ToDo> selectAll() {
    String sql = """
      select
        id,
        todo,
        detail,
        created_at as createdAt,
        updated_at updatedAt
      from
        todos
      order by
        id
    """;
    List<ToDo> list = new ArrayList<>();
    try ( 
      Connection con = dataSource.getConnection();
      PreparedStatement ps = con.prepareStatement( sql );
      ResultSet rs = ps.executeQuery() ) {
      
      while ( rs.next() ) {
        list.add( mapRow( rs ) );
      }
    } catch( SQLException e ) {
      throw new RuntimeException( e );
    }
    return list;
  }
  
  
  
  public ToDo selectById( Integer id ) {
    String sql = """
      select
        id,
        todo,
        detail,
        created_at as createdAt,
        updated_at as updatedAt
      from
        todos
      where
        id = ?
    """;
    try (
      Connection con = dataSource.getConnection();
      PreparedStatement ps = con.prepareStatement( sql ); ) {
      
      ps.setInt( 1, id );
      try (
        ResultSet rs = ps.executeQuery() ) {
        
        if ( rs.next() ) return mapRow( rs );
        return null;
      }
    } catch ( SQLException e ) {
      throw new RuntimeException( e );
    }
  }
  
  
  
  public int insert( ToDo toDo ) {
    String sql = """
      insert into
        todos( todo, detail, created_at, updated_at )
        values( ?, ?, current_timestamp, current_timestamp )
    """;
    try (
      Connection con = dataSource.getConnection();
      PreparedStatement ps = con.prepareCall( sql ); ) {
      
      ps.setString( 1, toDo.getTodo() );
      ps.setString( 2, toDo.getDetail() );
      return ps.executeUpdate();
    } catch ( SQLException e ) {
      throw new RuntimeException( e );
    }
  }
  
  
  
  public int update( ToDo toDo ) {
    String sql = """
      update
        todos
        set
          todo = ?,
          detail = ?,
          updated_at = current_timestamp
      where
        id = ?
    """;
    try (
      Connection con = dataSource.getConnection();
      PreparedStatement ps = con.prepareStatement( sql ) ) {
      
      ps.setString( 1, toDo.getTodo() );
      ps.setString( 2, toDo.getDetail() );
      ps.setInt( 3, toDo.getId() );
      return ps.executeUpdate(); 
    } catch( SQLException e ) {
      throw new RuntimeException( e );
    }
  }
  
  
  
  public int delete( Integer id ) {
    String sql = "delete from todos where id = ?";
    try (
      Connection con = dataSource.getConnection();
      PreparedStatement ps = con.prepareStatement( sql ) ) {
      
      ps.setInt( 1, id );
      return ps.executeUpdate();
    } catch( SQLException e ) {
      throw new RuntimeException( e );
    }
 
  }

}
