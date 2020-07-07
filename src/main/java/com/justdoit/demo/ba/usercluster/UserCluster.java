package com.justdoit.demo.ba.usercluster;

import lombok.Data;

@Data
public class UserCluster {

	private String name;
	private String username;
	private Integer type;
	private String filters;
	private String sql;
	private String result;
	private Long size;

}