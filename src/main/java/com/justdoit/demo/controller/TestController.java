package com.justdoit.demo.controller;

import java.util.Locale;

import javax.validation.Valid;

import com.justdoit.demo.mapper.UserMapper;
import com.justdoit.demo.model.RestResponse;
import com.justdoit.demo.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
	@Qualifier("kafkaTemplateOne")
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

		kafkaTemplate.send("one", String.valueOf(System.currentTimeMillis()));
		kafkaTemplate.send("two", "hello world");
		kafkaTemplate.send("three", "hello world");
	}

	@KafkaListener(topics = "one", groupId = "group-id")
	public void listenTopicOne(String message) {
		System.out.println("Received Message from topic one in group - group-id: " + message);
	}

	@KafkaListener(topics = "two", groupId = "group-id")
	public void listenTopicTwo(String message) {
		System.out.println("Received Message From topic two in group - group-id: " + message);
	}

	@KafkaListener(topics = "three", groupId = "group-id")
	public void listenTopicThree(String message) {
		System.out.println("Received Message From Topic three in group - group-id: " + message);
	}



}