package com.example.rs.ftn.ConnectSocialNetworkProject.model.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Ban {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long banId;
	 
	 @Column(unique = false,nullable = false)
	 private LocalDate timestamp;
	 
	 @ManyToOne
	 @JoinColumn(name = "userId", nullable = true) //towards
	 private User bannedUser;
	 
	 @ManyToOne
	 @JoinColumn(name = "groupId", nullable = true) //for
	 private Group bannedGroup;
	
	 
	 @ManyToOne
	 @JoinColumn(name = "username", nullable = false) 
	 private User byAdmin;
	 
	 
//	 @ManyToOne
//	    @JoinColumns({
//	            @JoinColumn(name = "group_id", referencedColumnName = "groupId"),
//	            @JoinColumn(name = "username", referencedColumnName = "username")
//	    })
//	  private GroupAdminId groupAdminId;
//	 
	 	 
	 public Ban(Long banId,LocalDate timestamp) {
		 this.banId = banId;
		 this.timestamp = timestamp;
	 }

	public Long getBanId() {
		return banId;
	}

	public void setBanId(Long banId) {
		this.banId = banId;
	}

	public LocalDate getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDate timestamp) {
		this.timestamp = timestamp;
	}

	public User getBannedUser() {
		return bannedUser;
	}

	public void setBannedUser(User bannedUser) {
		this.bannedUser = bannedUser;
	}

	public Group getBannedGroup() {
		return bannedGroup;
	}

	public void setBannedGroup(Group bannedGroup) {
		this.bannedGroup = bannedGroup;
	}

}
