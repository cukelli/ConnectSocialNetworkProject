package com.example.rs.ftn.ConnectSocialNetworkProject.model.dto;

import java.time.LocalDate;

import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.User;

public class GroupDTO {
	

	private Long groupId;
	private String name;
    private String description;
	private LocalDate createdAt;
	private boolean isSuspended;
	private String suspendedReason;
	private User groupAdmin;
	
	
	public GroupDTO(Long groupId, String name, String description, LocalDate createdAt, boolean isSuspended,
			String suspendedReason, User groupAdmin) {
		super();
		this.groupId = groupId;
		this.name = name;
		this.description = description;
		this.createdAt = createdAt;
		this.isSuspended = isSuspended;
		this.suspendedReason = suspendedReason;
		this.groupAdmin = groupAdmin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isSuspended() {
		return isSuspended;
	}

	public void setSuspended(boolean isSuspended) {
		this.isSuspended = isSuspended;
	}

	public String getSuspendedReason() {
		return suspendedReason;
	}

	public void setSuspendedReason(String suspendedReason) {
		this.suspendedReason = suspendedReason;
	}

	public User getGroupAdmin() {
		return groupAdmin;
	}

	public void setGroupAdmin(User groupAdmin) {
		this.groupAdmin = groupAdmin;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	


}
