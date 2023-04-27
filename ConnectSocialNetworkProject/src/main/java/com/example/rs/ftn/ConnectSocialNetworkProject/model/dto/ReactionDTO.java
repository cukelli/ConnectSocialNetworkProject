package com.example.rs.ftn.ConnectSocialNetworkProject.model.dto;

import java.time.LocalDate;

import com.example.rs.ftn.ConnectSocialNetworkProject.enumeration.ReactionType;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Comment;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Post;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.User;

public class ReactionDTO {
	
	private Long reactionId;
	private ReactionType type;
	private LocalDate timestamp;
    private User userReacted;
	private Post postReplied;
	private Comment commentReplied;
	
	public ReactionDTO() {}

	public ReactionDTO(Long reactionId, ReactionType type, LocalDate timestamp, User userReacted, Post postReplied,
			Comment commentReplied) {
		super();
		this.reactionId = reactionId;
		this.type = type;
		this.timestamp = timestamp;
		this.userReacted = userReacted;
		this.postReplied = postReplied;
		this.commentReplied = commentReplied;
	}

	public Long getId() {
		return reactionId;
	}

	public void setId(Long reactionId) {
		this.reactionId = reactionId;
	}

	public ReactionType getType() {
		return type;
	}

	public void setType(ReactionType type) {
		this.type = type;
	}

	public LocalDate getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDate timestamp) {
		this.timestamp = timestamp;
	}

	public User getUserReacted() {
		return userReacted;
	}

	public void setUserReacted(User userReacted) {
		this.userReacted = userReacted;
	}

	public Post getPostReplied() {
		return postReplied;
	}

	public void setPostReplied(Post postReplied) {
		this.postReplied = postReplied;
	}

	public Comment getCommentReplied() {
		return commentReplied;
	}

	public void setCommentReplied(Comment commentReplied) {
		this.commentReplied = commentReplied;
	}
		
}

