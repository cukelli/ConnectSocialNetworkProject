	package com.example.rs.ftn.ConnectSocialNetworkProject.model.entity;
	
	import java.time.LocalDate;
	
	import javax.persistence.Column;
	import javax.persistence.Entity;
	import javax.persistence.EnumType;
	import javax.persistence.Enumerated;
	import javax.persistence.GeneratedValue;
	import javax.persistence.GenerationType;
	import javax.persistence.Id;
	import javax.persistence.JoinColumn;
	import javax.persistence.ManyToOne;
	
	import com.example.rs.ftn.ConnectSocialNetworkProject.enumeration.ReactionType;
	
	@Entity
	public class Reaction {
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long reactionId;
		
		@Enumerated(EnumType.STRING)
		private ReactionType type;
		
		@Column(nullable = false)
		private LocalDate timestamp;
		 
		 @Column(nullable = false, unique = false)
		 private boolean isDeleted;
		
		@ManyToOne
	    @JoinColumn(name = "usernameUserReacted") 
	    private User userReacted;
		
		@ManyToOne
		@JoinColumn(name = "postId",nullable = true)
		private Post postReacted;
		
		@ManyToOne
		@JoinColumn(name = "commentId")
		private Comment commentReactedTo;
		
		
		
		public Reaction() {}
	
		public Reaction(Long reactionId, ReactionType type, LocalDate timestamp) {
			super();
			this.reactionId = reactionId;
			this.type = type;
			this.timestamp = timestamp;
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
	
		public Long getReactionId() {
			return reactionId;
		}
	
		public void setReactionId(Long reactionId) {
			this.reactionId = reactionId;
		}
	
		public boolean isDeleted() {
			return isDeleted;
		}
	
		public void setDeleted(boolean isDeleted) {
			this.isDeleted = isDeleted;
		}
	
		public String getUserReacted() {
			return userReacted.getUsername();
		}
	
		public void setUserReacted(User userReacted) {
			this.userReacted = userReacted;
		}
	
		public Long getPostReacted() {
	        return postReacted != null ? postReacted.getId() : null;
		}
	
		public void setPostReacted(Post postReacted) {
			this.postReacted = postReacted;
		}
	
		public Long getCommentReactedTo() {
	        return commentReactedTo != null ? commentReactedTo.getId() : null;
		}
	
		public void setCommentReactedTo(Comment commentReactedTo) {
			this.commentReactedTo = commentReactedTo;
		}
		
	
	}
