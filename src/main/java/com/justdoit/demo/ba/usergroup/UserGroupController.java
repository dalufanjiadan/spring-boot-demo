package com.justdoit.demo.ba.usergroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/userGroup")
public class UserGroupController {

	@Autowired
	private UserGroupService userGroupService;

	@GetMapping("/hello")
	public void hello() {
		userGroupService.hello();
	}

}