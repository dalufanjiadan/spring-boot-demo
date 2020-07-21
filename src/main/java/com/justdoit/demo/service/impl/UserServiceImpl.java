package com.justdoit.demo.service.impl;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

		userMapper.insertUser(user);

		return RestResponse.ok(user);
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
		// 设置分页查询参数
		PageHelper.startPage(1, 2);
		List<User> users = userMapper.findAll();
		// 封装分页查询结果到 PageInfo 对象中以获取相关分页信息
		PageInfo pageInfo = new PageInfo(users);

		System.out.println(pageInfo);

		return RestResponse.ok(users);
	}

	@Override
	public RestResponse<User> getUserById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}