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

import com.example.rs.ftn.ConnectSocialNetworkProject.enumeration.ReactionType;
import com.example.rs.ftn.ConnectSocialNetworkProject.exception.PostNotFoundException;
import com.example.rs.ftn.ConnectSocialNetworkProject.exception.UserNotFoundException;
import com.example.rs.ftn.ConnectSocialNetworkProject.message.Message;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Comment;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Post;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Reaction;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.User;
import com.example.rs.ftn.ConnectSocialNetworkProject.requestModels.ReactionRequest;
import com.example.rs.ftn.ConnectSocialNetworkProject.security.JwtUtil;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.CommentService;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.PostService;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.ReactionService;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.UserService;

@RestController
@RequestMapping("/reaction")
public class ReactionController {
	
	private final ReactionService reactionService;
	private final UserService userService;
	private final PostService postService;
	private final CommentService commentService;
	private final JwtUtil jwtUtil;
	
	public ReactionController(ReactionService reactionService,UserService userService,PostService postService,
			CommentService commentService,JwtUtil jwtUtil) {
		this.reactionService = reactionService;
		this.userService = userService;
		this.postService = postService;
		this.commentService = commentService;
		this.jwtUtil = jwtUtil;
	}
	
	@GetMapping("/all")
	@ResponseBody
	public List<Reaction> getAllReactions(Authentication authentication) {
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
		List<Reaction> reactions = reactionService.findAllUndeletedReactions();
		
		
        return reactions;
		
	}
	
	
	@PostMapping("/add/{postId}")
	public ResponseEntity<Reaction> reactToPost(
	    Authentication authentication,
	    @RequestBody ReactionRequest reaction,
	    @PathVariable("postId") Long postId
	) {
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

	    Reaction existingReaction = reactionService.findByPostReactedAndUserReacted(post, userLogged);
	    if (existingReaction != null) {
	        ReactionType existingType = existingReaction.getType();
	        ReactionType newType = reaction.getType();
	        if ((existingType == ReactionType.LIKE && newType == ReactionType.LIKE)
	            || (existingType == ReactionType.DISLIKE && newType == ReactionType.DISLIKE)
	            || (existingType == ReactionType.HEART && newType == ReactionType.HEART)) {
	            return new ResponseEntity<>(existingReaction, HttpStatus.OK);
	        } else {
	            existingReaction.setType(reaction.getType());
	            return new ResponseEntity<>(reactionService.updateReaction(existingReaction), HttpStatus.OK);
	        }
	    }

	    Reaction newReaction = new Reaction();
	    newReaction.setPostReacted(post);
	    newReaction.setType(reaction.getType());
	    newReaction.setTimestamp(LocalDate.now());
	    newReaction.setCommentReactedTo(null);
	    newReaction.setUserReacted(userLogged);

	    return new ResponseEntity<>(reactionService.addReaction(newReaction), HttpStatus.OK);
	}




		
	@PostMapping("/add/comment/{commentId}")
	public ResponseEntity<Reaction> reactToComment(Authentication authentication,@RequestBody ReactionRequest reaction,
	        @PathVariable("commentId") Long commentId) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		
		
		 Comment comment = null;
		    try {
		        comment = commentService.findOne(commentId);
		    } catch (PostNotFoundException e) {
		        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found.");
		    }
		    
		    
		    Reaction existingReaction = reactionService.findByCommentReactedToAndUserReacted(comment, userLogged);
		    if (existingReaction != null) {
		        ReactionType existingType = existingReaction.getType();
		        ReactionType newType = reaction.getType();
		        if ((existingType == ReactionType.LIKE && newType == ReactionType.LIKE)
		            || (existingType == ReactionType.DISLIKE && newType == ReactionType.DISLIKE)
		            || (existingType == ReactionType.HEART && newType == ReactionType.HEART)) {
		            return new ResponseEntity<>(existingReaction, HttpStatus.OK);
		        } else {
		            existingReaction.setType(reaction.getType());
		            return new ResponseEntity<>(reactionService.updateReaction(existingReaction), HttpStatus.OK);
		        }
		    }
		    
		
		Reaction newReaction = new Reaction();
		newReaction.setCommentReactedTo(comment);
		newReaction.setType(reaction.getType());
		newReaction.setTimestamp(LocalDate.now());
		newReaction.setUserReacted(userLogged);
		
		return new ResponseEntity<>(reactionService.addReaction(newReaction),HttpStatus.OK);
	}
	
	@PutMapping("/update/{reactionId}")
	public ResponseEntity<Reaction> updateReaction(Authentication authentication,@RequestBody ReactionRequest reaction,
			@PathVariable("reactionId") Long id) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
			
		
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		Reaction oldReaction = reactionService.findOne(id);
		oldReaction.setType(reaction.getType());
		
		if (oldReaction.getUserReacted().toString() != userLogged.getUsername().toString()) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"You are not creator of this reaction.");
		}
		
		
		Reaction updatedReaction = reactionService.updateReaction(oldReaction);
		return new ResponseEntity<>(updatedReaction,HttpStatus.OK);
		
	}
	
	
	@DeleteMapping("/delete/{reactionId}")
	public Message deleteReaction(Authentication authentication,@PathVariable("reactionId") Long reactionId) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
			
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		
		Reaction reactionForDeletion = reactionService.findOne(reactionId);
		
		if (!reactionForDeletion.getUserReacted().equals(userLogged.getUsername()) ||
				!reactionForDeletion.getUserReacted().equals("admin")) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"You are not creator of this reaction.");
		}
		
		reactionForDeletion.setDeleted(true);
		 reactionService.updateReaction(reactionForDeletion);
		return new Message("Reaction deleted succesfuly.");
		
	}
	
	
	

}
