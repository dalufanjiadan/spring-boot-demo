package com.justdoit.demo.mapper;

import java.util.List;

import com.justdoit.demo.model.Todo;
import com.justdoit.demo.payload.UpdateTodoRequest;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TodoMapper {

	List<Todo> findTodos(String username, int status);

	int insertTodo(Todo todo);

	@Delete("delete from todo where id = #{todoId}")
	int deleteTodo(long todoId);

	@Update("update todo set text = #{updateTodoRequest.text}, status = #{updateTodoRequest.status} where id =#{todoId}")
	int updateTodo(long todoId, UpdateTodoRequest updateTodoRequest);

}