package com.example.rs.ftn.ConnectSocialNetworkProject.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class GroupRequest {
	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private Long groupRequestId;
	   
	   @Column(nullable = false)
	    private boolean approved;
	   
	   @Column(nullable = false)
	   private LocalDateTime createdAt;
	   
	   @Column(nullable = false)
	   private LocalDateTime at;
	   
	   @ManyToOne(fetch = FetchType.LAZY)
	   @JoinColumn(name = "username")
	   private User user;
	   
	   public GroupRequest() {}

	public GroupRequest(Long groupRequestId, boolean approved, LocalDateTime createdAt, LocalDateTime at, User user) {
		super();
		this.groupRequestId = groupRequestId;
		this.approved = approved;
		this.createdAt = createdAt;
		this.at = at;
		this.user = user;
	}

	public Long getId() {
		return groupRequestId;
	}

	public void setId(Long groupRequestId) {
		this.groupRequestId = groupRequestId;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getAt() {
		return at;
	}

	public void setAt(LocalDateTime at) {
		this.at = at;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	  	   
}
