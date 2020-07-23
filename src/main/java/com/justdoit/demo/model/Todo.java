package com.justdoit.demo.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Todo {

	private String username;
	private Long id;
	private String description;
	private int status;
	private LocalDateTime createAt;

}