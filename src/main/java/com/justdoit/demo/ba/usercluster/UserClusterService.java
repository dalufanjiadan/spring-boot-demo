package com.justdoit.demo.ba.usercluster;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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

		if (filterGroup2.size() > 0) {
			Map<String, Object> result = new HashMap<>();
			List<Map<String, Object>> filters = new ArrayList<>();
			for (Map<String, Object> filter : filterGroup2) {

				Map<String, Object> map = new HashMap<>();
				map.put("label", filter.get("filterName"));
				map.put("paramsName", filter.get("params_name"));
				map.put("paramsType", filter.get("params_type"));

				filters.add(map);
			}

			result.put("group2Name", filterGroup2.get(0).get("group2Name"));
			result.put("filters", filters);
			return result;
		}

		return null;
	}

}