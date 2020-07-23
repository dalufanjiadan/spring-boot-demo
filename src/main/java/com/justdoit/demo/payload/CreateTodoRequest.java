package com.justdoit.demo.payload;

import lombok.Data;

@Data
public class CreateTodoRequest {
	private String username;
	private String text;
}