package com.example.rs.ftn.ConnectSocialNetworkProject.model.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Comment {
	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private Long commentId;
	   
	   @Column(nullable = false, unique = false)
	   private String text;
	   
	   @Column(nullable = false, unique = false)
	   private LocalDate timestamp;
	   
	   @Column(nullable = false, unique = false)
	   private boolean isDeleted;
	   
	   @ManyToOne(fetch = FetchType.LAZY)
	   @JoinColumn(name = "username")
	    private User userCommented;
	   
	   @OneToMany(mappedBy = "commentReactedTo")
	   private List<Reaction> reactions;
	   
	    @ManyToOne
	    @JoinColumn(name = "postId")
	    private Post commentedPost;
	    
		 @OneToMany(mappedBy = "reportedComment")
		 private List<Report> reports;

	    
	    @ManyToOne
		@JoinColumn(name = "parentCommentId", insertable = false, updatable = false)
		private Comment parentComment;
	    
	    @OneToMany(mappedBy = "parentComment")
		private List<Comment> replies;

	   
	   public Comment() {}   

	public Comment(Long commentId, String text, LocalDate timestamp, boolean isDeleted, User userCommented,
			Post belongsTo) {
		super();
		this.commentId = commentId;
		this.text = text;
		this.timestamp = timestamp;
		this.isDeleted = isDeleted;
		this.userCommented = userCommented;
	}

	public Long getId() {
		return commentId;
	}

	public void setId(Long commentId) {
		this.commentId = commentId;
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

	public User getUser() {
		return userCommented;
	}

	public void setUser(User userCommented) {
		this.userCommented = userCommented;
	}

	public Post getCommentedPost() {
		return commentedPost;
	}

	public void setCommentedPost(Post commentedPost) {
		this.commentedPost = commentedPost;
	}

	public List<Reaction> getReactions() {
		return reactions;
	}

	public void setReactions(List<Reaction> reactions) {
		this.reactions = reactions;
	}

	public Comment getParentComment() {
		return parentComment;
	}

	public void setParentComment(Comment parentComment) {
		this.parentComment = parentComment;
	}

	public List<Comment> getReplies() {
		return replies;
	}

	public void setReplies(List<Comment> replies) {
		this.replies = replies;
	}
	

}
