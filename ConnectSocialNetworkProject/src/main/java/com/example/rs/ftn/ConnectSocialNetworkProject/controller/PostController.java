package com.example.rs.ftn.ConnectSocialNetworkProject.controller;

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
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Post;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.User;
import com.example.rs.ftn.ConnectSocialNetworkProject.requestModels.PostRequest;
import com.example.rs.ftn.ConnectSocialNetworkProject.requestModels.PostUpdate;
import com.example.rs.ftn.ConnectSocialNetworkProject.security.JwtUtil;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.PostService;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.UserService;

@RestController
@RequestMapping("/post")
public class PostController {
	
	private final PostService postService;
	private final UserService userService;

	private final JwtUtil jwtUtil;

	
	public PostController(PostService postService,UserService
			userService,JwtUtil jwtUtil) {
		this.postService = postService;
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}
	
	@GetMapping("/all")
	@ResponseBody
	public List<Post> getAllPosts(Authentication authentication) {
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
		List<Post> posts = postService.findAll();
		
        return posts;
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<Post> addPost(Authentication authentication,@RequestBody PostRequest post) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		
		Post newPost = new Post();
		newPost.setContent(post.getContent());
		newPost.setCreationDate(post.getCreationDate());
		newPost.setUser(userLogged);
		
		
		return new ResponseEntity<>(postService.addPost(newPost),HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<Post> updatePostApi(Authentication authentication,@RequestBody PostUpdate post) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
			
		
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		Post oldPost = postService.findOne(post.getPostId());
		oldPost.setContent(post.getContent());
		
		if (oldPost.getUser().toString() != userLogged.getUsername().toString()) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"You are not creator of this post.");
		}
		//oldPost.setUser(userLogged);
		
		
		Post updatedPost = postService.updatePost(oldPost);
		return new ResponseEntity<>(updatedPost,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deletePost(Authentication authentication,@PathVariable("id") Long id) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
			
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		
		Post postForDeletion = postService.findOne(id);
		
		if (postForDeletion.getUser().toString() != userLogged.getUsername().toString() ||
				!postForDeletion.getUser().equals("admin")) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"You are not creator of this post.");
		}
		
		 postService.remove(id);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@GetMapping("/user")
	@ResponseBody
	public ResponseEntity<List<Post>> getUserPosts(Authentication authentication) {
		String userUsername = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(userUsername);
			
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		
		if (!userLogged.getUsername().toString().equals(userLogged.getUsername()) && !userLogged.getRole().toString().equals("ADMIN")) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not admin/you are not creator of those posts.");

		}

		if (userLogged.getRole().toString().equals("ADMIN")) {
			List<Post> posts = postService.findAll();
			return new ResponseEntity<>(posts,HttpStatus.OK);
		}
		
		List<Post> posts = postService.getUserPosts(userLogged.getUsername());
		return new ResponseEntity<>(posts,HttpStatus.OK);

	}
	
	
	
}
