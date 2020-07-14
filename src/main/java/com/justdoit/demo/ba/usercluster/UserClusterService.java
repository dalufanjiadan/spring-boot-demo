package com.justdoit.demo.ba.usercluster;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

@Service
public class UserClusterService {

	@Autowired
	private UserClusterMapper mapper;

	public Object getUserClusterTypes() {

		return mapper.getUserClusterTypes();
	}

	public Object getFilterGroup12() {

		List<Map<String, Object>> data = mapper.getFilterGroup12();

		Map<String, List<Map<String, Object>>> dataGrouped = data.stream()
				.collect(groupingBy(map -> map.get("group1Name").toString()));

		// 构造result
		List<Map<String, Object>> result = new ArrayList<>();
		// 按照特定顺序返回
		List<String> group1Names = new ArrayList<>(Arrays.asList("登陆类", "付费类", "用户属性"));

		for (String group1Name : group1Names) {
			Map<String, Object> map = new HashMap<>();
			List<Map<String, Object>> children = new ArrayList<>();
			// 构造children
			for (Map<String, Object> group2 : dataGrouped.get(group1Name)) {
				Map<String, Object> group2Map = new HashMap<>();
				group2Map.put("label", group2.get("group2Name"));
				group2Map.put("uri", "/api/userCluster/filterGroup2/" + group2.get("group2Id"));
				children.add(group2Map);
			}
			map.put("label", group1Name);
			map.put("children", children);
			result.add(map);
		}

		return result;
	}

	public Object getFilterGroup2(Integer group2Id) {

		List<Map<String, Object>> filterGroup2 = mapper.getFilterGroup2(group2Id);
		Splitter splitter = Splitter.on(",").trimResults();

		if (filterGroup2.size() > 0) {
			Map<String, Object> result = new HashMap<>();
			List<UserClusterFilter> filters = new ArrayList<>();
			for (Map<String, Object> map : filterGroup2) {

				UserClusterFilter filter = new UserClusterFilter();
				filter.setGroup2Name(map.get("group2Name").toString());
				filter.setName(map.get("filterName").toString());
				filter.setParamsName(splitter.splitToList(map.get("params_name").toString()));
				filter.setParamsType(splitter.splitToList(map.get("params_type").toString()));
				filter.setSql(map.get("sql").toString());
				filters.add(filter);
			}

			result.put("group2Name", filterGroup2.get(0).get("group2Name"));
			result.put("filters", filters);
			return result;
		}

		return null;
	}

	public Object createUserCluster(UserClusterRequest userClusterRequest) {

		UserCluster userCluster = new UserCluster();

		// 交并差 INTERSECT UNION EXCEPT
		ImmutableMap<String, String> setOperationsMap = ImmutableMap.of("0", "INTERSECT", "1", "UNION", "2", "EXCEPT");

		// 账号/角色/设备
		Integer type = userClusterRequest.getType();

		List<UserClusterFilter> filters = userClusterRequest.getFilters(); // 分群筛选条件
		List<String> setOperations = userClusterRequest.getSetOperations(); // 集合操作 第一个默认为并集
		String filtersStr = ""; // 显示的分群筛选条件描述
		String sql = ""; // 最终的组装SQL
		for (int i = 0; i < filters.size(); i++) {
			UserClusterFilter filter = filters.get(i);
			filtersStr += filter.getValuedName();
			sql += getSql(type, filter.getValuedSql());
			if (i != filters.size() - 1) {
				sql += setOperationsMap.get(setOperations.get(i + 1)) + "\n";
			}
		}

		userCluster.setFilters(filtersStr);
		userCluster.setType(type);
		userCluster.setUsername(userClusterRequest.getUsername());
		userCluster.setName(userClusterRequest.getName());
		userCluster.setSql(sql);

		// 去计算，提交个异步任务
		// 组装好SQL后与数仓交互
		userCluster.setResult("数仓 url ");
		userCluster.setSize(100000L);

		System.out.println(sql);
		System.out.println(userCluster);

		mapper.insertUserCluster(userCluster);

		return userCluster;
	}

	public String getSql(int type, String filter) {

		String field;
		String tableName;

		// 用type来区分表
		if (0 == type) {
			field = "account_id";
			








			tableName = "dm_gamelog_account_ds";
		} else if (1 == type) {
			field = "role_id";
			tableName = "dm_gamelog_role_ds";
		} else {
			field = "device_id";
			tableName = "dm_adm_transform_device_ds";
		}

		return String.format("select DISTINCT(%s) from %s where %s \n", field, tableName, filter);
	}

}