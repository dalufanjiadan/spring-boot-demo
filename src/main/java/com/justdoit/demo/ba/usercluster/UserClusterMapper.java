package com.justdoit.demo.ba.usercluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserClusterMapper {

	@Select("SELECT id as value, name as label FROM user_cluster_filter_group_1")
	List<Map<String, Object>> getUserClusterTypes();

	List<Map<String, Object>> getFilterGroup12();

	List<Map<String, Object>> getFilterGroup2(int group2Id);

	int insertUserCluster(UserCluster userCluster);

}