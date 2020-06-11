package com.justdoit.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SignUpRequest {
	private String username;
	private String password;
}