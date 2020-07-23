package com.justdoit.demo.controller;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class AAA {

	Integer a = 10;
	String b = null;
	Map<String, Object> data;

}