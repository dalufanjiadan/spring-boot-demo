package com.justdoit.demo.ba.usercluster;

import java.util.List;

import lombok.Data;

@Data
public class UserClusterRequest {

	private String name;
	private String username;
	private Integer type;
	private List<UserClusterFilter> filters;
	private List<String> setOperations;

}