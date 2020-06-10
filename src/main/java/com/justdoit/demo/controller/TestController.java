package com.justdoit.demo.controller;

import java.util.Locale;

import javax.validation.Valid;

import com.justdoit.demo.mapper.UserMapper;
import com.justdoit.demo.model.RestResponse;
import com.justdoit.demo.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private UserMapper userMapper;

	@ApiOperation(value = "1 hello")
	@PostMapping("/hello")
	public RestResponse<String> hello(@RequestBody @Valid User user) {

		return RestResponse.ok("hello world");
	}

	@ApiOperation(value = "2 lang")
	@GetMapping("/lang")
	public Object lang() {
		Locale locale = LocaleContextHolder.getLocale();
		System.out.println(messageSource.getMessage("hello", null, locale));

		return RestResponse.ok("hello");
	}


	@GetMapping("/mapper")
	public void mapper() {
		

		System.out.println(userMapper.findAll());
	}


}