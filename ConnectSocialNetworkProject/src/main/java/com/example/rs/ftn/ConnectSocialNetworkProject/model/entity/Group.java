package com.example.rs.ftn.ConnectSocialNetworkProject.model.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "groupTable") //Group/groups su rezervisane reci

public class Group {
	
	 @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private Long groupId;
	   
	   @Column(nullable = false, unique = false)
	   private String name;
	   
	   @Column(nullable = false, unique = false)
	   private String description;
	   
	   @Column(nullable = false)
	   private LocalDate createdAt;
	   
	   @Column(nullable = false)
	   private boolean isSuspended;
	   
	   @Column(nullable = true)
	   private String suspendedReason;
	   
	   @OneToMany(mappedBy = "adminGroup", cascade = CascadeType.ALL)
	   private List<GroupAdmin> admins;
	   
	   @OneToMany(mappedBy = "groupPosted")
	   private List<Post> posts;
	   
	   public Group() {}
	  

	  public Group(Long groupId, String name, String description, LocalDate createdAt, boolean isSuspended,
			String suspendedReason, List<GroupAdmin> admins, List<Post> posts) {
		super();
		this.groupId = groupId;
		this.name = name;
		this.description = description;
		this.createdAt = createdAt;
		this.isSuspended = isSuspended;
		this.suspendedReason = suspendedReason;
		this.admins = admins;
		this.posts = posts;
	}



	public Long getGroupId() {
		return groupId;
	}


	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}


	public List<GroupAdmin> getAdmins() {
		return admins;
	}


	public void setAdmins(List<GroupAdmin> admins) {
		this.admins = admins;
	}


	public Long getId() {
		return groupId;
	}

	public void setId(Long groupId) {
		this.groupId = groupId;
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



	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	     
	


}
