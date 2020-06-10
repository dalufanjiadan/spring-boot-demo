package com.justdoit.demo.service.impl;

import java.util.List;

import com.justdoit.demo.mapper.UserMapper;
import com.justdoit.demo.model.RestResponse;
import com.justdoit.demo.model.User;
import com.justdoit.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public RestResponse<User> createUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RestResponse<User> deleteUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RestResponse<User> updateUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RestResponse<List<User>> getAllUser() {
		return RestResponse.ok(userMapper.findAll());
	}

	@Override
	public RestResponse<User> getUserById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}