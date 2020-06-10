package com.justdoit.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

	@Select("select * from user")
	List<Map<String, Object>> findAll();
}