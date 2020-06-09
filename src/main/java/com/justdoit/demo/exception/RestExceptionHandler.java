package com.justdoit.demo.exception;

import com.justdoit.demo.model.RestResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(Exception.class)
	protected Object exception(Exception e) {

		return RestResponse.badRequest();
	}
}