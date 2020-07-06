package com.justdoit.demo.ba.usercluster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserClusterService {

	@Autowired
	private UserClusterMapper mapper;

	public Object getUserClusterTypes() {
		
		return mapper.getUserClusterTypes();
	}

}