package com.example.rs.ftn.ConnectSocialNetworkProject.model.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class GroupAdmin {
	
	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private Long groupAdminId;
	   
	   @ManyToOne(fetch = FetchType.EAGER)
	   @JoinColumn(name = "username")
	   private User user;
	   
	   @ManyToOne(fetch = FetchType.EAGER)
	   @JoinColumn(name ="groupId")
	   private Group adminGroup;
	   
	   public GroupAdmin() {}
	   
	   
	   public GroupAdmin(User user, Group adminGroup) {
	        this.user = user;
	        this.adminGroup = adminGroup;
	    }


	public Long getGroupAdminId() {
		return groupAdminId;
	}

	public String getUser() {
		return user.getUsername();
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Long getAdminGroup() {
		return adminGroup.getId();
	}


	public void setAdminGroup(Group adminGroup) {
		this.adminGroup = adminGroup;
	}
	

}
