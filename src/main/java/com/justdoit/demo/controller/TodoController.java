package com.justdoit.demo.controller;

import java.util.List;

import com.justdoit.demo.model.RestResponse;
import com.justdoit.demo.model.Todo;
import com.justdoit.demo.payload.CreateTodoRequest;
import com.justdoit.demo.payload.UpdateTodoRequest;
import com.justdoit.demo.service.TodoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/todos")
@Api(tags = "todo")
public class TodoController {

	@Autowired
	private TodoService service;

	@PostMapping
	@ApiOperation(value = "1 新建todo")
	public RestResponse<Object> createTodo(@RequestBody CreateTodoRequest createTodoRequest) {
		return RestResponse.ok(service.createTodo(createTodoRequest));
	}

	@DeleteMapping("/{todoId}")
	@ApiOperation(value = "2 删除todo")
	public RestResponse<Object> deleteTodo(@PathVariable long todoId) {

		return RestResponse.ok(service.deleteTodo(todoId));
	}

	@PutMapping("/{todoId}")
	@ApiOperation(value = "3 更新todo")
	public RestResponse<Object> updateTodo(@PathVariable long todoId,
			@RequestBody UpdateTodoRequest updateTodoRequest) {

		return RestResponse.ok(service.updateTodo(todoId, updateTodoRequest));

	}

	@GetMapping
	@ApiOperation(value = "4 获取所有todo")
	@ApiImplicitParams({ //
			@ApiImplicitParam(name = "username", value = "用户名"),
			@ApiImplicitParam(name = "status", value = "未完成：0 完成：1 所有：2fasdfac", dataType = "int"),
			@ApiImplicitParam(name = "currentPage", value = "current page", dataType = "int"),
			@ApiImplicitParam(name = "pageSize", value = "page size", dataType = "int"), })
	public Object getTodos(//
			@RequestParam String username, //
			@RequestParam int status, //
			@RequestParam int currentPage, //
			@RequestParam int pageSize)//
	{
		return RestResponse.ok(service.getTodos(username, status, currentPage, pageSize));
	}

}