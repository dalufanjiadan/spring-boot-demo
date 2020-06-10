package com.justdoit.demo.exception;

import com.justdoit.demo.model.RestResponse;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

	/**
	 * MethodArgumentNotValidException 异常处理
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Object methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
		BindingResult bindingResult = e.getBindingResult();
		for (ObjectError error : bindingResult.getAllErrors()) {
			// 返回检验信息
			return RestResponse.validationException(error.getDefaultMessage());
		}
		return RestResponse.badRequest();
	}

	@ExceptionHandler(Exception.class)
	protected Object exception(Exception e) {

		return RestResponse.badRequest();
	}
}