package com.example.rs.ftn.ConnectSocialNetworkProject.model.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class GroupAdmin {
	
	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private Long groupAdminId;
	   
	   @OneToMany(mappedBy = "byGroupAdmin")
	   private List<Ban> bans;
	   
	   @ManyToOne
	   @JoinColumn(name = "groupId", referencedColumnName = "groupId")
	   private Group adminGroup;
	   
	   public GroupAdmin() {}
	   
	   public GroupAdmin(Long groupAdminId) {
		   this.groupAdminId = groupAdminId;
	   }

	  public Long getGroupAdminId() {
		return groupAdminId;
	 }

	  public void setGroupAdminId(Long groupAdminId) {
		this.groupAdminId = groupAdminId;
	 }

	  public List<Ban> getBans() {
	  	return bans;
	 }

	 public void setBans(List<Ban> bans) {
		this.bans = bans;
	 }
	   



}
