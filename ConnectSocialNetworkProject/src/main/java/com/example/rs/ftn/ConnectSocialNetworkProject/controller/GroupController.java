package com.example.rs.ftn.ConnectSocialNetworkProject.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.rs.ftn.ConnectSocialNetworkProject.exception.UserNotFoundException;
import com.example.rs.ftn.ConnectSocialNetworkProject.message.Message;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Group;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.GroupAdmin;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Post;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.User;
import com.example.rs.ftn.ConnectSocialNetworkProject.requestModels.GroupRequestCreate;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.GroupAdminService;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.GroupService;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.UserService;

@RestController
@RequestMapping("/group")
public class GroupController {
	
	private final GroupService groupService;
	
	private final UserService userService;
	
	private final GroupAdminService groupAdminService;
	
	public GroupController(GroupService groupService,UserService userService,GroupAdminService groupAdminService) {
		this.groupAdminService = groupAdminService;
		this.groupService = groupService;
		this.userService = userService;
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<Group> addGroup(Authentication authentication,@RequestBody GroupRequestCreate group) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		
		Group newGroup = new Group();
		newGroup.setName(group.getName());
		newGroup.setDescription(group.getDescription());
		newGroup.setCreatedAt(LocalDate.now());
		newGroup.setSuspended(false);
		newGroup.setDeleted(false);
		newGroup.setSuspendedReason(null);

		
		Group groupCreated = groupService.addGroup(newGroup);
		GroupAdmin newGroupAdmin = new GroupAdmin(userLogged,groupCreated);
		groupAdminService.addGroupAdmin(newGroupAdmin);
		
		return new ResponseEntity<>(newGroup,HttpStatus.OK);
	}
	
	
	@GetMapping("/all")
	@ResponseBody
	public List<Group> getAllGroups(Authentication authentication) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		if (!userLogged.getRole().toString().equals("ADMIN")) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not admin");
		}
	
		List<Group> groups = groupService.findAll();
		
        return groups;
        
	}
	
	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public Message deleteGrouo(Authentication authentication,@PathVariable("id") Long id) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
			
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		
		Group groupForDeletion = groupService.findOne(id);
		boolean admin = false;
		for (GroupAdmin groupAdmin: groupForDeletion.getAdmins()) {
			if (groupAdmin.getUser().equals(userLogged.getUsername())) {
				admin = true;
				break;
			}
			
		}
		 if (userLogged.getRole().toString().equals("ADMIN")) {
			 admin = true;
		    }
		 if (!admin) {
		        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not admin of this group.");

		 }
		 
		 groupService.remove(id);
		return new Message("Sucesfuly delete.");	
	}
	
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Group> updateGroupApi(Authentication authentication,@RequestBody GroupRequestCreate group,
			@PathVariable("id") Long id) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
			
		
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		Group oldGroup = groupService.findOne(id);
		oldGroup.setDescription(group.getDescription());
		oldGroup.setName(group.getName());
		
		
		boolean admin = false;
		for (GroupAdmin groupAdmin: oldGroup.getAdmins()) {
			if (groupAdmin.getUser().equals(userLogged.getUsername())) {
				admin = true;
				break;
			}
			
		}
		 if (userLogged.getRole().toString().equals("ADMIN")) {
			 admin = true;
		    }
		 if (!admin) {
		        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not admin of this group.");

		 }
		
		 Group updatedGroup = groupService.updateGroup(oldGroup);
		return new ResponseEntity<>(updatedGroup,HttpStatus.OK);
		
		
	
	}
		
}
