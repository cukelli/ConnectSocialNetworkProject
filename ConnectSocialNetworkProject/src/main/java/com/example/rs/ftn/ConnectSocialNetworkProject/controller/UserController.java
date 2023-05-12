package com.example.rs.ftn.ConnectSocialNetworkProject.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

import com.example.rs.ftn.ConnectSocialNetworkProject.enumeration.Role;
import com.example.rs.ftn.ConnectSocialNetworkProject.exception.UserNotFoundException;
import com.example.rs.ftn.ConnectSocialNetworkProject.message.Message;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.User;
import com.example.rs.ftn.ConnectSocialNetworkProject.requestModels.ChangePasswordRequest;
import com.example.rs.ftn.ConnectSocialNetworkProject.requestModels.JwtReturn;
import com.example.rs.ftn.ConnectSocialNetworkProject.requestModels.UserLogin;
import com.example.rs.ftn.ConnectSocialNetworkProject.requestModels.UserRegister;
import com.example.rs.ftn.ConnectSocialNetworkProject.security.JwtUtil;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		if (!userLogged.getRole().toString().equals("ADMIN")) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not admin");
		}
		List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);

	}
	
	@GetMapping("find/{username}")
	public ResponseEntity<User> getUserById(@PathVariable("username") String username) {
		User user = null;
		try {
			user = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
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
		User user = null;
		try {
			user = userService.findOne(userLogin.getUsername());
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		boolean mathces = passwordEncoder.matches(userLogin.getPassword(), user.getPassword());
		if(!mathces) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");

		}
		String token = jwtUtil.generateToken((UserDetails) userLogin);
		return new JwtReturn(token);
		
	}
	
	
	@GetMapping("/")
	@ResponseBody
	public User getUserFromToken(Authentication authentication) { //detalji korisnika
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}

        return userLogged;
	}
	
	
	@PostMapping("/registration")
	@ResponseBody
	public Message registration(@Valid @RequestBody UserRegister userRegister,BindingResult result) {
		
		 if (result.hasErrors()) {
		        String errorMessage = result.getFieldErrors()
		                .stream()
		                .map(FieldError::getDefaultMessage)
		                .collect(Collectors.joining("; "));
		        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
		    }

		
		User existingUser = null;
		try {
			existingUser = userService.findOne(userRegister.getUsername());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this username already exists.");
		} catch (UserNotFoundException e) {
		}
		 try {
			   existingUser = userService.findOneByEmail(userRegister.getEmail());
		        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this email already exists.");
		    } catch (UserNotFoundException e) {
		    }
		 
		String encodedPassword = passwordEncoder.encode(userRegister.getPassword());
		User newUser = new User(Role.USER,userRegister.getUsername(), encodedPassword, userRegister.getEmail(),
				userRegister.getFirstName(), userRegister.getLastName(),null,LocalDateTime.now(),false);
	    userService.addUser(newUser);
	    return new Message("Registration successful.");	
	}
	
	
	@PostMapping("/changePassword")
	public ResponseEntity<Message> changePassword(@RequestBody ChangePasswordRequest request,Authentication authentication) {
	    String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		
		
	    if (!userService.checkPassword(userLogged, request.getOldPassword())) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect old password.");
	    }
	    
	    if (request.getNewPassword().length() < 8) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password must be at least 8 characters long.");
	    }
	    userService.changePassword(userLogged.getUsername(),request.getOldPassword(), request.getNewPassword(),request.getRepeatNewPassword());
	    return new ResponseEntity<>(new Message("Password changed successfully."), HttpStatus.OK);
	}	
	

}
