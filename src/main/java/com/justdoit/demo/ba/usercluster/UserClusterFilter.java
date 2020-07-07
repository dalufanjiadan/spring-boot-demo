package com.justdoit.demo.ba.usercluster;

import java.util.List;

import lombok.Data;

@Data
public class UserClusterFilter {

	private String name;
	private List<String> paramsName;
	private List<String> paramsType;
	private String sql;

}