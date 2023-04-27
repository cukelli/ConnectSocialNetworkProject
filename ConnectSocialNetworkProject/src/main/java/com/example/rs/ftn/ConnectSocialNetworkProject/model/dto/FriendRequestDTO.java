package com.example.rs.ftn.ConnectSocialNetworkProject.model.dto;

import java.time.LocalDateTime;

import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.User;

public class FriendRequestDTO {

    private Long friendRequestId;

    private boolean approved;

    private LocalDateTime createdAt;

    private LocalDateTime at;
    
    private User sentBy;
    
    private User sentFor;
    
    public FriendRequestDTO() {}

    public FriendRequestDTO(Long friendRequestId, boolean approved, LocalDateTime createdAt, LocalDateTime at,
    		User sentBy,User sentFor) {
        this.friendRequestId = friendRequestId;
        this.approved = approved;
        this.createdAt = createdAt;
        this.at = at;
        this.sentBy = sentBy;
        this.sentFor = sentFor;
    }

	public Long getId() {
		return friendRequestId;
	}

	public void setId(Long friendRequestId) {
		this.friendRequestId = friendRequestId;
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

	public User getSentBy() {
		return sentBy;
	}

	public void setSentBy(User sentBy) {
		this.sentBy = sentBy;
	}

	public User getSentFor() {
		return sentFor;
	}

	public void setSentFor(User sentFor) {
		this.sentFor = sentFor;
	}
	

}
