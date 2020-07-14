// package com.justdoit.demo.exception;

// import java.util.HashMap;
// import java.util.Map;

// import com.justdoit.demo.model.RestResponse;

// import org.springframework.validation.BindingResult;
// import org.springframework.validation.ObjectError;
// import org.springframework.validation.FieldError;
// import org.springframework.web.bind.MethodArgumentNotValidException;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.RestControllerAdvice;

// @RestControllerAdvice
// public class RestExceptionHandler {

// 	/**
// 	 * MethodArgumentNotValidException 异常处理
// 	 */
// 	// @ExceptionHandler(MethodArgumentNotValidException.class)
// 	// public Object methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
// 	// 	BindingResult bindingResult = e.getBindingResult();
// 	// 	for (ObjectError error : bindingResult.getAllErrors()) {
// 	// 		// 返回检验信息
// 	// 		return RestResponse.validationException(error.getDefaultMessage());
// 	// 	}
// 	// 	return RestResponse.badRequest();
// 	// }

// 	@ExceptionHandler(MethodArgumentNotValidException.class)
// 	public Object handleValidationExceptions(MethodArgumentNotValidException ex) {
// 		Map<String, String> errors = new HashMap<>();
// 		ex.getBindingResult().getAllErrors().forEach((error) -> {
// 			String fieldName = ((FieldError) error).getField();
// 			String errorMessage = error.getDefaultMessage();
// 			errors.put(fieldName, errorMessage);
// 		});
// 		// return errors;
// 		return RestResponse.validationException(errors);
// 	}

// 	@ExceptionHandler(Exception.class)
// 	protected Object exception(Exception e) {

// 		return RestResponse.badRequest();
// 	}
// }