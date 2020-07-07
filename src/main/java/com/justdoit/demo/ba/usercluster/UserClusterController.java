package com.justdoit.demo.ba.usercluster;

import com.justdoit.demo.model.RestResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/userCluster")
public class UserClusterController {

	@Autowired
	private UserClusterService service;

	@GetMapping("/hello")
	public void hello() {
		System.out.println("hello world");
	}

	/**
	 * 用户包类型
	 * 
	 * 设备/账号/角色
	 * 
	 * @return
	 */
	@GetMapping("/types")
	public RestResponse<Object> getUserClusterTypes() {

		return RestResponse.ok(service.getUserClusterTypes());
	}

	/**
	 * 过滤条件两级分组
	 * 
	 * @return
	 */
	@GetMapping("/filterGroup12")
	public RestResponse<Object> getFilterGroup12() {

		return RestResponse.ok(service.getFilterGroup12());
	}

	/**
	 * 获取二级过滤的详细包含条件
	 * 
	 * 活跃用户/新增用户
	 * 
	 * @return
	 */
	@GetMapping("/filterGroup2/{group2Id}")
	public RestResponse<Object> getFilterGroup2(@PathVariable Integer group2Id) {

		return RestResponse.ok(service.getFilterGroup2(group2Id));
	}

	/**
	 * 新建用户分群
	 * 
	 * @return
	 */
	@PostMapping
	public RestResponse<Object> createUserCluster(@RequestBody UserClusterRequest userClusterRequest) {

		return RestResponse.ok(service.createUserCluster(userClusterRequest));
	}

}