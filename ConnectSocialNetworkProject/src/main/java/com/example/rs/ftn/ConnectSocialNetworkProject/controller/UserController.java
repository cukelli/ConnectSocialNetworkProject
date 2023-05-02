package com.example.rs.ftn.ConnectSocialNetworkProject.controller;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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

import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.User;
import com.example.rs.ftn.ConnectSocialNetworkProject.requestModels.JwtReturn;
import com.example.rs.ftn.ConnectSocialNetworkProject.requestModels.UserLogin;
import com.example.rs.ftn.ConnectSocialNetworkProject.security.JwtUtil;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;
	private final JwtUtil jwtUtil;
	
	public UserController(UserService userService,JwtUtil jwtUtil) {
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}
	
	
	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllUsers(Authentication authentication) {
		System.out.println(authentication.getName());
		String username = authentication.getName();
		User userLogged = userService.findOne(username);
		System.out.println(userLogged.getRole().toString());
		if (!userLogged.getRole().toString().equals("ADMIN")) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not admin");
		}
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
	
	@PostMapping("/login")
	@ResponseBody
	public JwtReturn login(@RequestBody UserLogin userLogin) {
		System.out.println(userLogin.getUsername());
		User loggedUser = userService.findOneByUsernameAndPassword(userLogin.getUsername(),userLogin.getPassword());
		String token = jwtUtil.generateToken((UserDetails) userLogin);
		return new JwtReturn(token);
		
	}

}
