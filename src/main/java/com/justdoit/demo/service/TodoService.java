package com.justdoit.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.justdoit.demo.mapper.TodoMapper;
import com.justdoit.demo.model.Todo;
import com.justdoit.demo.payload.CreateTodoRequest;
import com.justdoit.demo.payload.UpdateTodoRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

	@Autowired
	private TodoMapper mapper;

	/**
	 * 获取todo 列表
	 * 
	 * @param username    用户名
	 * @param status      todo 状态 筛选条件 完成/未完成
	 * @param currentPage
	 * @param pageSize
	 * 
	 * @return List<Todo>
	 */
	public Map<String, Object> getTodos(String username, int status, int currentPage, int pageSize) {

		// 设置分页查询参数
		PageHelper.startPage(currentPage, pageSize);
		List<Todo> todos = mapper.findTodos(username, status);
		// 封装分页查询结果到 PageInfo 对象中以获取相关分页信息
		PageInfo pageInfo = new PageInfo(todos);

		Map<String, Object> result = new HashMap<>();
		result.put("total", pageInfo.getTotal());
		result.put("todos", todos);

		return result;
	}

	public Object createTodo(CreateTodoRequest createTodoRequest) {

		Todo todo = new Todo(createTodoRequest);
		mapper.insertTodo(todo);

		return todo;
	}

	public Object deleteTodo(long todoId) {

		return mapper.deleteTodo(todoId);
	}

	public Object updateTodo(long todoId, UpdateTodoRequest updateTodoRequest) {

		return mapper.updateTodo(todoId, updateTodoRequest);
	}

}