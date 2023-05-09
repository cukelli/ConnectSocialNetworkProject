package com.example.rs.ftn.ConnectSocialNetworkProject.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rs.ftn.ConnectSocialNetworkProject.service.GroupService;

@RestController
@RequestMapping("/group")
public class GroupController {
	
	private final GroupService groupService;
	
	public GroupController(GroupService groupService) {
		this.groupService = groupService;
		
	}


}
