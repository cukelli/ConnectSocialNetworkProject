package com.example.rs.ftn.ConnectSocialNetworkProject.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.rs.ftn.ConnectSocialNetworkProject.exception.UserNotFoundException;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.FriendRequest;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Group;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.User;
import com.example.rs.ftn.ConnectSocialNetworkProject.requestModels.AnswerFriendRequest;
import com.example.rs.ftn.ConnectSocialNetworkProject.requestModels.FriendRequestCreate;
import com.example.rs.ftn.ConnectSocialNetworkProject.security.JwtUtil;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.FriendRequestService;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.UserService;

@RestController
@RequestMapping("/friendRequest")
public class FriendRequestController {
	
	private final UserService userService;
	private final FriendRequestService friendRequestService;
	private final JwtUtil jwtUtil;
	
	public FriendRequestController(UserService userService,FriendRequestService friendRequestService, JwtUtil jwtUtil) {
		super();
		this.userService = userService;
		this.friendRequestService = friendRequestService;
		this.jwtUtil = jwtUtil;
	}
	
	
	@PostMapping("/add")
	public ResponseEntity<FriendRequest> createFriendRequest(Authentication authentication,@RequestBody 
			FriendRequestCreate friendRequest) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		
		FriendRequest newFriendRequest = new FriendRequest();
		newFriendRequest.setCreatedAt(LocalDateTime.now());
		newFriendRequest.setApproved(false);
		newFriendRequest.setAt(null);
	    String usernameSentTo = friendRequest.getSentFor();
	    User userSentTo = userService.findOne(usernameSentTo);
	    
	    if (userSentTo == null) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
	    }
	    
	    FriendRequest existingFriendRequest = friendRequestService.findBySentByAndSentFor(userLogged, userSentTo);
        if (existingFriendRequest != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Friend request already sent.");
        }

        newFriendRequest.setSentFor(userSentTo);
		
        newFriendRequest.setSentBy(userLogged);		
        FriendRequest friendRequestCreated = friendRequestService.addFriendRequest(newFriendRequest);
		
		return new ResponseEntity<>(newFriendRequest,HttpStatus.OK);
	}
	
	
	@GetMapping("/user")
	@ResponseBody
	public ResponseEntity<List<FriendRequest>> getUserFriendRequests(Authentication authentication) {
	    String userUsername = authentication.getName();
	    User userLogged = null;
	    try {
	        userLogged = userService.findOne(userUsername);
	    } catch (UserNotFoundException e) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
	    }

	    if (!userLogged.getUsername().equals(userUsername) && !userLogged.getRole().toString().equals("ADMIN")) {
	        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not admin/those are not yout friend requests.");
	    }

	    List<FriendRequest> friendRequests;
	    if (userLogged.getRole().toString().equals("ADMIN")) {
	    	friendRequests = friendRequestService.findAll();
	    } else {
	    	friendRequests = friendRequestService.findByApprovedFalse(userLogged);
	         System.out.println(friendRequests);
	    }


	    return new ResponseEntity<>(friendRequests, HttpStatus.OK);
	}
	
	
	@GetMapping("/user/friends")
	@ResponseBody
	public ResponseEntity<List<User>> getUserFriends(Authentication authentication) {	
	    String userUsername = authentication.getName();
		 ArrayList<User> friends = new ArrayList<>();
	    User userLogged = null;
	    try {
	        userLogged = userService.findOne(userUsername);
	    } catch (UserNotFoundException e) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
	    }

	    if (!userLogged.getUsername().equals(userUsername) && !userLogged.getRole().toString().equals("ADMIN")) {
	        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not admin/those are not yout friends.");
	    }

	    List<FriendRequest> friendRequestsSentBy;
	    friendRequestsSentBy = friendRequestService.findAllByApprovedTrueAndSentBy(userLogged);
	    
	    List<FriendRequest> friendRequestsSentFor;
	    friendRequestsSentFor = friendRequestService.findAllByApprovedTrueAndSentFor(userLogged);
	    
	    if (friendRequestsSentBy.size() > 0) {
		    for (FriendRequest f: friendRequestsSentBy) {
		    	String friendUsername = f.getSentFor();
		    	User userFriend = userService.findOne(friendUsername);
		    	friends.add(userFriend);
		    }
	    }
	    
	    if (friendRequestsSentFor.size() > 0) {
		    for (FriendRequest f: friendRequestsSentFor) {
		    	String friendUsername = f.getSentBy();
		    	System.out.println(f.getSentBy());
		    	User userFriend = userService.findOne(friendUsername);
		    	friends.add(userFriend);
		    }
	    }
	    

	    return new ResponseEntity<List<User>>(friends, HttpStatus.OK);
	}
	
	
	
	@PostMapping("/answer/{friendRequestId}")
	public ResponseEntity<FriendRequest> answerFriendRequest
	(Authentication authentication, @RequestBody AnswerFriendRequest friendRequest,
			 @PathVariable("friendRequestId") Long friendRequestId) {
	    String username = authentication.getName();
	    User userLogged = null;
	    try {
	        userLogged = userService.findOne(username);
	    } catch (UserNotFoundException e) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
	    }
	    
	    FriendRequest existingFriendRequest = friendRequestService.findOne(friendRequestId);
	    if (existingFriendRequest == null) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Friend request not found.");
	    }
	    
	    existingFriendRequest.setApproved(friendRequest.isApproved());
	    existingFriendRequest.setAt(LocalDateTime.now());
	    
	    if (!existingFriendRequest.isApproved()){
	    	friendRequestService.remove(friendRequestId);
			return new ResponseEntity<>(existingFriendRequest,HttpStatus.OK);
	    }
	    
	    FriendRequest updatedFriendRequest = friendRequestService.updateFriendRequest(existingFriendRequest);

		return new ResponseEntity<>(updatedFriendRequest,HttpStatus.OK);


	}


	


}
