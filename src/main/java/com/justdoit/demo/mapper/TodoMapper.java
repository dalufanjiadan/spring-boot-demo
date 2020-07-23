package com.justdoit.demo.mapper;

import java.util.List;

import com.justdoit.demo.model.Todo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TodoMapper {

	List<Todo> findTodos(String username, int status);

	int insertTodo(Todo todo);

}