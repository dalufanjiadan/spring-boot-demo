package com.justdoit.demo.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import lombok.extern.slf4j.Slf4j;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.justdoit.demo.model.RestResponse;
import com.justdoit.demo.model.User;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper om;

	@Test
	public void createUser() throws JsonProcessingException, Exception {
		User user = new User();
		user.setUsername("username");
		user.setPassword("password");
		user.setPhone("13909999999");
		// 模拟POST请求
		MvcResult mvcResult = mockMvc//
				.perform(post("/api/v1/users")
						// 请求参数类型
						.contentType(MediaType.APPLICATION_JSON)
						// 接收参数类型
						.accept(MediaType.APPLICATION_JSON)
						// 请求参数
						.content(om.writeValueAsString(user)))
				// 结果验证
				.andReturn();

		MockHttpServletResponse mockResponse = mvcResult.getResponse();
		RestResponse<Map> response = om.readValue(mockResponse.getContentAsString(), RestResponse.class);

		// 断言验证数据
		Assertions.assertEquals(response.getCode(), 0);
		Assertions.assertEquals(response.getData().get("id"), 3);
		Assertions.assertEquals(response.getData().get("username"), "username");

	}

	@Test
	public void getAllUser() throws Exception {

		// this.mockMvc//
		// .perform(get("/api/v1/test/hello"))//
		// .andDo(print())//
		// .andExpect(status().isOk())//
		// .andExpect(jsonPath("code").value(0))//
		// .andExpect(jsonPath("data").value("hello world"));

		// 模拟GET请求，并返回结果
		MvcResult result = mockMvc//
				.perform(get("/api/v1/users")//
						.accept(MediaType.APPLICATION_JSON))//
				.andReturn();

		MockHttpServletResponse mockResponse = result.getResponse();
		RestResponse<List<Map>> response = om.readValue(mockResponse.getContentAsString(), RestResponse.class);

		// 断言验证数据
		Assertions.assertEquals(response.getCode(), 0);
		Assertions.assertEquals(response.getData().size(), 2);

	}

}