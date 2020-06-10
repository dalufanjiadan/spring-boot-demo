package com.justdoit.demo.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.justdoit.demo.validator.Phone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	private Long id;

	@NotBlank(message = "用户名不能为空")
	@Size(min = 2, max = 10, message = "用户名长度需满足2-10")
	private String username;

	@NotBlank(message = "密码不能为空")
	@Size(min = 6, max = 20, message = "密码长度需满足6-20")
	// @JsonIgnore
	private String password;

	// @Phone
	private String phone;

}