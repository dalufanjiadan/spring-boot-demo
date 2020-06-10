package com.justdoit.demo.controller;

import javax.validation.Valid;

import com.justdoit.demo.model.User;
import com.justdoit.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api(tags = "users")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping
	public Object createUser(@RequestBody @Valid User user) {

		return null;
	}

	@GetMapping
	public Object getAllUser() {
		return userService.getAllUser();
	}
}