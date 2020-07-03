package com.justdoit.demo.ba.usergroup;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserGroupMapper {

	@Select("select * from user")
	public List<Map<String, Object>> findAll();

}