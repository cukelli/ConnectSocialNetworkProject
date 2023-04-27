package com.example.rs.ftn.ConnectSocialNetworkProject.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.User;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	
	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);

	}
	
	@GetMapping("find/{username}")
	public ResponseEntity<User> getUserById(@PathVariable("username") String username) {
		User user = userService.findOne(username);
        return new ResponseEntity<>(user, HttpStatus.OK);

	}
	
	@PostMapping("/add")//   /user/add
	public ResponseEntity<User> addUser(@RequestBody User user) {
		User newUser = userService.addUser(user);
		return new ResponseEntity<>(newUser,HttpStatus.CREATED);
	}
	
	@PutMapping("/update")
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		User updatedUser = userService.updateUser(user);
		return new ResponseEntity<>(updatedUser,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/delete/{username}")
	public ResponseEntity<?> deleteUser(@PathVariable("username") String username) {
		 userService.remove(username);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}

}
