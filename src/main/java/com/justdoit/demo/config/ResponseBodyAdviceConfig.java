package com.justdoit.demo.config;

import java.util.Locale;

import com.justdoit.demo.model.RestResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class ResponseBodyAdviceConfig implements ResponseBodyAdvice<RestResponse> {

	@Autowired
	private MessageSource messageSource;

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return returnType.getParameterType().isAssignableFrom(RestResponse.class);
	}

	@Override
	public RestResponse beforeBodyWrite(RestResponse body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		Locale locale = LocaleContextHolder.getLocale();
		String message = messageSource.getMessage(body.getMessage(), null, locale);
		body.setMessage(message);
		return body;
	}
}