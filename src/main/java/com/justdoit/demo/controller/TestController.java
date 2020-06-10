package com.justdoit.demo.controller;

import javax.validation.Valid;

import com.justdoit.demo.model.RestResponse;
import com.justdoit.demo.model.User;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "test")
@RestController
@RequestMapping("/api/v1/test")
public class TestController {

	@ApiOperation(value = "1 hello")
	@PostMapping("/hello")
	public RestResponse<String> hello(@RequestBody @Valid User user) {

		return RestResponse.ok("hello world");
	}
}