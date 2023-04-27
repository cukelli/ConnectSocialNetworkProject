package com.example.rs.ftn.ConnectSocialNetworkProject.model.dto;

import java.time.LocalDateTime;

import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.User;

public class GroupRequestDTO {
	
	private int id;
    private boolean approved;
	private LocalDateTime createdAt;
	private LocalDateTime at;//odnosi se na trenutak kada je zahtev prihvacen/odbijen
	private User createdBy;


	
	public GroupRequestDTO() {}
	
	public GroupRequestDTO(int id, boolean approved, LocalDateTime createdAt, LocalDateTime at,User createdBy) {
		super();
		this.id = id;
		this.approved = approved;
		this.createdAt = createdAt;
		this.at = at;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	


}
