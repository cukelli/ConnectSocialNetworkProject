package com.example.rs.ftn.ConnectSocialNetworkProject.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.example.rs.ftn.ConnectSocialNetworkProject.exception.UserNotFoundException;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Group;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.GroupRequest;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.User;
import com.example.rs.ftn.ConnectSocialNetworkProject.requestModels.GroupJoinRequestCreate;
import com.example.rs.ftn.ConnectSocialNetworkProject.security.JwtUtil;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.GroupRequestService;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.GroupService;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.UserService;

@Controller
@RequestMapping("/groupRequest")
public class GroupRequestController {
	
	private final UserService userService;
	private final GroupService groupService;
	private final GroupRequestService groupRequestService;
	private final JwtUtil jwtUtil;
	
	public GroupRequestController(UserService userService,GroupRequestService groupRequestService, JwtUtil jwtUtil,
			GroupService groupService) {
		super();
		this.userService = userService;
		this.groupRequestService = groupRequestService;
		this.groupService = groupService;
		this.jwtUtil = jwtUtil;
	}
	
	@PostMapping("/add")
	public ResponseEntity<GroupRequest> createGroupRequest(Authentication authentication,@RequestBody GroupJoinRequestCreate groupRequest) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		
		GroupRequest newGroupRequest = new GroupRequest();
		newGroupRequest.setCreatedAt(LocalDateTime.now());
		newGroupRequest.setApproved(false);
		newGroupRequest.setAt(null);
	    Long groupId = groupRequest.getGroupId();
	    Group group = groupService.findOne(groupId);
	    
	    if (group == null) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found.");
	    }
	    
	 // Check if a GroupRequest already exists for the user and group
        GroupRequest existingGroupRequest = groupRequestService.findByUserAndGroup(userLogged, group);
        if (existingGroupRequest != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Group request already exists for the user and group.");
        }

	    newGroupRequest.setGroup(group);
		
		newGroupRequest.setUser(userLogged);		
		GroupRequest groupRequestCreated = groupRequestService.addGroupRequest(newGroupRequest);
		
		return new ResponseEntity<>(newGroupRequest,HttpStatus.OK);
	}
	
	@GetMapping("/{groupId}")
	public ResponseEntity<GroupRequest> getGroupRequest(Authentication authentication, 
			@PathVariable("groupId") Long groupId) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
	    User user = userService.findOne(username);
	    Group group = groupService.findOne(groupId);

	    if (user == null || group == null) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User or group not found.");
	    }

	    GroupRequest groupRequest = groupRequestService.findByUserAndGroup(user, group);

	    if (groupRequest == null) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Group request not found.");
	    }

	    return ResponseEntity.ok(groupRequest);
	}
	
	
	

}
