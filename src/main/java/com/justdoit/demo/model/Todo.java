package com.justdoit.demo.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.justdoit.demo.payload.CreateTodoRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Todo {

	private String username;
	private Long id;
	private String text;
	private int status = 0;
	private LocalDateTime createAt;

	public Todo(CreateTodoRequest createTodoRequest) {
		this.username = createTodoRequest.getUsername();
		this.text = createTodoRequest.getText();
	}

}