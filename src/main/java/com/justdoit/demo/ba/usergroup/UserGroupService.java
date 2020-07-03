package com.justdoit.demo.ba.usergroup;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGroupService {

	@Autowired
	private UserGroupMapper mapper;

	public void hello() {

		List<Map<String, Object>> users = mapper.findAll();

		System.out.println(users);

	}

}