package com.justdoit.demo.ba.usercluster;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UserClusterFilter {

	private String group2Name;
	private String name;
	private List<String> paramsName;
	private List<String> paramsType;
	private List<String> paramsValue;
	private String sql;

	/**
	 * 获取参数替换后的SQL
	 */
	@JsonIgnore
	public String getValuedSql() {

		for (int i = 0; i < paramsName.size(); i++) {
			sql = sql.replaceAll(paramsName.get(i), paramsValue.get(i));
		}

		return sql;
	}

	/**
	 * 获取参数替换后的name
	 */
	@JsonIgnore
	public String getValuedName() {

		for (int i = 0; i < paramsName.size(); i++) {
			name = name.replaceAll(paramsName.get(i), paramsValue.get(i));
		}

		return group2Name + ":" + name + "\n";
	}

}