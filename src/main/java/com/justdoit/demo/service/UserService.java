package com.justdoit.demo.service;

import java.util.List;

import com.justdoit.demo.model.RestResponse;
import com.justdoit.demo.model.User;

public interface UserService {

	public RestResponse<User> createUser(User user);

	public RestResponse<User> deleteUser(User user);

	public RestResponse<User> updateUser(User user);

	public RestResponse<List<User>> getAllUser();

	public RestResponse<User> getUserById(Long id);
}