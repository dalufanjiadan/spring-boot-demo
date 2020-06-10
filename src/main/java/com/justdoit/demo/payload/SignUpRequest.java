package com.justdoit.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SignUpRequest {
	String username;
	String password;
}