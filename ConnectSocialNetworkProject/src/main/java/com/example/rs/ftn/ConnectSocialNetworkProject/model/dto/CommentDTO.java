package com.example.rs.ftn.ConnectSocialNetworkProject.model.dto;

import java.time.LocalDate;

import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Post;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.User;

public class CommentDTO {
	
	private Long commentId;
	private String text;
	private LocalDate timestamp;
	private boolean isDeleted;
	private User userCommented;
	private Post belongsTo;
	
	public CommentDTO() {}

	public CommentDTO(Long commentId, String text, LocalDate timestamp, boolean isDeleted, User userCommented,
			Post belongsTo) {
		super();
		this.commentId = commentId;
		this.text = text;
		this.timestamp = timestamp;
		this.isDeleted = isDeleted;
		this.userCommented = userCommented;
		this.belongsTo = belongsTo;
	}




	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LocalDate getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDate timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public User getUserCommented() {
		return userCommented;
	}

	public void setUserCommented(User userCommented) {
		this.userCommented = userCommented;
	}

	public Post getBelongsTo() {
		return belongsTo;
	}

	public void setBelongsTo(Post belongsTo) {
		this.belongsTo = belongsTo;
	}


}
