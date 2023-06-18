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

import com.example.rs.ftn.ConnectSocialNetworkProject.exception.PostNotFoundException;
import com.example.rs.ftn.ConnectSocialNetworkProject.exception.UserNotFoundException;
import com.example.rs.ftn.ConnectSocialNetworkProject.message.Message;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Comment;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Post;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.User;
import com.example.rs.ftn.ConnectSocialNetworkProject.requestModels.CommentRequest;
import com.example.rs.ftn.ConnectSocialNetworkProject.security.JwtUtil;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.CommentService;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.PostService;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.UserService;

@RestController
@RequestMapping("/comment")
public class CommentController {
	
	private final CommentService commentService;
	private final UserService userService;
	private final PostService postService;
	private final JwtUtil jwtUtil;
	
	public CommentController(CommentService commentService,UserService
			userService,PostService postService,JwtUtil jwtUtil) {
		this.commentService = commentService;
		this.userService = userService;
		this.postService = postService;
		this.jwtUtil = jwtUtil;
	}
	
	@GetMapping("/all")
	@ResponseBody
	public List<Comment> getAllComment(Authentication authentication) {
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
		List<Comment> comments = commentService.findAllUndeletedComments();
		for (Comment comment: comments) {
			System.out.println(comment.getId());
		}
		
        return comments;
		
	}
	
	
	@PostMapping("/add/{postId}")
	public ResponseEntity<Comment> addComment(Authentication authentication,@RequestBody CommentRequest comment,
	        @PathVariable("postId") Long postId) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		
		
		 Post post = null;
		    try {
		        post = postService.findOne(postId);
		    } catch (PostNotFoundException e) {
		        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found.");
		    }
		
		Comment newComment = new Comment();
		newComment.setText(comment.getText());
		newComment.setTimestamp(LocalDate.now());
		newComment.setUser(userLogged);
	    newComment.setCommentedPost(post);
		
		return new ResponseEntity<>(commentService.addComment(newComment),HttpStatus.OK);
	}
	
	@PutMapping("/update/{commentId}")
	public ResponseEntity<Comment> updateComment(Authentication authentication,@RequestBody CommentRequest comment,
			@PathVariable("commentId") Long id) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
			
		
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		Comment oldComment = commentService.findOne(id);
		oldComment.setText(comment.getText());
		
		if (oldComment.getUser().toString() != userLogged.getUsername().toString()) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"You are not creator of this comment.");
		}
		//oldPost.setUser(userLogged);
		
		
		Comment updatedComment = commentService.updateComment(oldComment);
		return new ResponseEntity<>(updatedComment,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/delete/{id}")
	public Message deleteComment(Authentication authentication,@PathVariable("id") Long id) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
			
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		
		Comment commentForDeletion = commentService.findOne(id);
		
		if (!commentForDeletion.getUser().equals(userLogged.getUsername()) ||
				!commentForDeletion.getUser().equals("admin")) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"You are not creator of this comment.");
		}
		
		commentForDeletion.setDeleted(true);
		 commentService.updateComment(commentForDeletion);
		return new Message("Comment deleted succesfuly.");
		
	}
	
	@GetMapping("/post/{postId}")
	@ResponseBody
	public List<Comment> getCommentsByPostId(Authentication authentication,@PathVariable("postId") Long postId) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
			
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
	    Post post = postService.findOne(postId);
		
	    List<Comment> comments = commentService.findAllByCommentedPostAndParentCommentIsNullAndIsDeletedFalse(post);
	    return comments;
	}
	
	
		@GetMapping("/{commentId}")
		public Object getCommenttDetails(Authentication authentication, @PathVariable("commentId") Long commentId) {
		    String username = authentication.getName();
		    User userLogged = null;
		    try {
		        userLogged = userService.findOne(username);
		    } catch (UserNotFoundException e) {
		        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		    }
		    
		    Comment comment = commentService.findOne(commentId);
		    
		    return comment;
		   
		}
		
		@PostMapping("/reply/{postId}/{commentId}")
		public ResponseEntity<Comment> addReply(Authentication authentication, @RequestBody CommentRequest comment,
		        @PathVariable("commentId") Long id, @PathVariable("postId") Long postId) {
		    String username = authentication.getName();
		    User userLogged = null;
		    try {
		        userLogged = userService.findOne(username);
		    } catch (UserNotFoundException e) {
		        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		    }

		    Comment parentComment = commentService.findOne(id);
		    if (parentComment == null) {
		        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent comment not found.");
		    }
		    Post post = postService.findOne(postId);
		 

		    Comment newReply = new Comment();
		    newReply.setText(comment.getText());
		    newReply.setTimestamp(LocalDate.now());
		    newReply.setUser(userLogged);
		    newReply.setParentComment(parentComment);
		    newReply.setCommentedPost(post);

		    return new ResponseEntity<>(commentService.addComment(newReply), HttpStatus.OK);
		}
		
	

	
}
