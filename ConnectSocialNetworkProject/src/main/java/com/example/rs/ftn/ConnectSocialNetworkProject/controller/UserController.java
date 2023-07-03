package com.example.rs.ftn.ConnectSocialNetworkProject.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.FriendRequest;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Group;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.GroupAdmin;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.GroupRequest;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Image;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.User;
import com.example.rs.ftn.ConnectSocialNetworkProject.requestModels.ChangePasswordRequest;
import com.example.rs.ftn.ConnectSocialNetworkProject.requestModels.JwtReturn;
import com.example.rs.ftn.ConnectSocialNetworkProject.requestModels.UserLogin;
import com.example.rs.ftn.ConnectSocialNetworkProject.requestModels.UserRegister;
import com.example.rs.ftn.ConnectSocialNetworkProject.requestModels.UserUpdate;
import com.example.rs.ftn.ConnectSocialNetworkProject.security.JwtUtil;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.FriendRequestService;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.GroupAdminService;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.GroupRequestService;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.GroupService;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.ImageService;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private final UserService userService;
	private final ImageService imageService;
	private final GroupAdminService groupAdminService;
	private final GroupRequestService groupRequestService;
	private final GroupService groupService;
	private final JwtUtil jwtUtil;
	private final FriendRequestService friendRequestService;

	
	public UserController(UserService userService,ImageService imageService,
			GroupAdminService groupAdminService,GroupRequestService groupRequestService,GroupService groupService,
			JwtUtil jwtUtil, FriendRequestService friendRequestService) {
		this.userService = userService;
		this.imageService = imageService;
		this.groupAdminService = groupAdminService;
		this.groupRequestService = groupRequestService;
		this.groupService = groupService;
		this.jwtUtil = jwtUtil;
		this.friendRequestService=friendRequestService;
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
	
	
	@GetMapping("/all/allUsersNotMe")
	public ResponseEntity<List<User>> getAllUsersExceptMe(Authentication authentication) {
		System.out.println(authentication.getName());
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		
		List<User> friends = new ArrayList<>();
		List<FriendRequest> friendRequestsSentBy;
	    friendRequestsSentBy = friendRequestService.findBySentBy(userLogged);
	    
	    List<FriendRequest> friendRequestsSentFor;
	    friendRequestsSentFor = friendRequestService.findBySentFor(userLogged);
	    
	    for (FriendRequest f: friendRequestsSentBy) {
	    	String friendUsername = f.getSentFor();
	    	User userFriend = userService.findOne(friendUsername);
	    	friends.add(userFriend);
	    }
	    
	    for (FriendRequest f: friendRequestsSentFor) {
	    	String friendUsername = f.getSentBy();
	    	System.out.println(f.getSentBy());
	    	User userFriend = userService.findOne(friendUsername);
	    	friends.add(userFriend);
	    }
		List<User> users = userService.findAll();
		if (friends.size() > 0 ) {
			for(User u: friends) {
				users.remove(u);
			}
		}
		users.remove(userLogged);
		try {
		users.remove(userService.findOne("admin"));
		} catch (Exception e) {
			return new ResponseEntity<>(users, HttpStatus.OK);
		}
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
		
		Image profileImage = imageService.findTopByPostedImageBy_UsernameOrderByCreatedAtDesc(username);
		user.setProfileImage(profileImage);
        return new ResponseEntity<>(user, HttpStatus.OK);

	}
	
	@PostMapping("/add")//   /user/add
	public ResponseEntity<User> addUser(@RequestBody User user) {
		User newUser = userService.addUser(user);
		return new ResponseEntity<>(newUser,HttpStatus.CREATED);
	}
	
	@PutMapping("/update")
	public ResponseEntity<User> updateUser(Authentication authentication, @RequestBody UserUpdate user) {
	    String username = authentication.getName();
	    User userLogged = null;
	    try {
	        userLogged = userService.findOne(username);
	    } catch (UserNotFoundException e) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
	    }

	    userLogged.setFirstName(user.getFirstName());
	    userLogged.setLastName(user.getLastName());
	    userLogged.setUsername(user.getUsername());
	    userLogged.setEmail(user.getEmail());
	    userLogged.setDescription(user.getDescription());

        Image image = new Image();
        image.setPath(user.getImage());
        image.setPostedImageBy(userLogged);
        userLogged.setProfileImage(image); 
        imageService.addImage(image);
	    

	    User updatedUser = userService.updateUser(userLogged);
	    updatedUser.setProfileImage(image); 
	    return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}
	
	
	
	@DeleteMapping("/delete/{username}")
	public ResponseEntity<?> deleteUser(@PathVariable("username") String username) {
		 userService.remove(username);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	
	@GetMapping("/getUserGroups")
	@ResponseBody
	public ResponseEntity<List<Group>> getUserGroups(Authentication authentication) {
		 ArrayList<Group> groups = new ArrayList<>();
		 String username = authentication.getName();
		    User userLogged = null;
		    try {
		        userLogged = userService.findOne(username);
		    } catch (UserNotFoundException e) {
		        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		    }
		    List<GroupAdmin> groupAdmins = groupAdminService.findAllByUser(userLogged);
		    List<GroupRequest> groupRequests = groupRequestService.findByUser(userLogged);
		    for (GroupAdmin g: groupAdmins) {
		    	Long adminGroupId = g.getAdminGroup();
		    	Group adminGroup = groupService.findOne(adminGroupId);
		        groups.add(adminGroup);
		    	
		    }
		    
		    for (GroupRequest g: groupRequests) {
		    	Long groupRequestGroupId = g.getGroup();
		    	Group groupRequestedGroup = groupService.findOne(groupRequestGroupId);
		        groups.add(groupRequestedGroup);
		    	
		    }
		    
		    return new ResponseEntity<>(groups, HttpStatus.OK);		    
		    
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
		
		Image profileImage = imageService.findTopByPostedImageBy_UsernameOrderByCreatedAtDesc(username);
		if (profileImage != null) {
			userLogged.setProfileImage(profileImage);

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
				userRegister.getFirstName(), userRegister.getLastName(),null,null,LocalDateTime.now(),false);
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