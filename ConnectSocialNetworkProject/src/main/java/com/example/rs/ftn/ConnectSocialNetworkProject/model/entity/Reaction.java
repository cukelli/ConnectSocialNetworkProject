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
	
	@ManyToOne
    @JoinColumn(name = "usernameUserReacted") 
    private User userReacted;
	
	@ManyToOne
	@JoinColumn(name = "postId")
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


}
