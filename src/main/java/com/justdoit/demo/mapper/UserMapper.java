package com.justdoit.demo.mapper;

import java.util.List;
import java.util.Map;

import com.justdoit.demo.model.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

	int insertUser(User user);

	@Select("select * from users")
	List<User> findAll();
}