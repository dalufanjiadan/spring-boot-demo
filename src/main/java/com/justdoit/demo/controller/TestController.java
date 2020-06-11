package com.justdoit.demo.controller;

import java.util.Locale;

import javax.validation.Valid;

import com.justdoit.demo.mapper.UserMapper;
import com.justdoit.demo.model.RestResponse;
import com.justdoit.demo.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.StringUtils;
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
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private UserMapper userMapper;

	@ApiOperation(value = "1 hello")
	@GetMapping("/hello")
	public RestResponse<String> hello() {

		send();
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
	public Object mapper() {

		System.out.println(userMapper.findAll());

		return RestResponse.ok(userMapper.findAll());
	}

	// 发送消息方法
	public void send() {

		kafkaTemplate.send("test", "hello world");
	}

	@KafkaListener(topics = "test", groupId = "group-id")
	public void listen(String message) {
		System.out.println("Received Messasge in group - group-id: " + message);
	}

}