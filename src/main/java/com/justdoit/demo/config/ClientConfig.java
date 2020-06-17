package com.justdoit.demo.config;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import lombok.Data;

@Data
public class ClientConfig {
	public static Map<String, String> clients = ImmutableMap.of("13902",
			"8da768f6a156c08ac56840f2d7caee65dd24dd0ad0bef6d7ab9efe950e2953d6");

}