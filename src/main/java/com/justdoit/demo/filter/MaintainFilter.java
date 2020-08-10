package com.justdoit.demo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * MaintainFilter
 */
@Component
@Order(1)
@ConditionalOnProperty(value = "enable.maintain", havingValue = "true", matchIfMissing = false)
public class MaintainFilter implements Filter {

	@Autowired
	private ObjectMapper om;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		ImmutableMap<String, Object> result = ImmutableMap.of("code", 503, "message", "维护中");

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(om.writeValueAsString(result));
	}

}
