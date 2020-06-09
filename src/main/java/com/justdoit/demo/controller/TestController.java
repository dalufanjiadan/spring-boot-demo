package com.justdoit.demo.controller;

import com.justdoit.demo.model.RestResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "test")
@RestController
@RequestMapping("/api/v1/test")
public class TestController {

	@ApiOperation(value = "1 hello")
	@GetMapping("/hello")
	public RestResponse<String> hello() {

		int a=1/0;

		return RestResponse.ok("hello world"); 
	}
}