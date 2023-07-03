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
public class FriendRequest {
	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private Long friendRequestId;
	   
	   @Column(nullable = true)
	   private boolean approved;
	   
	   @Column(nullable = false,unique = false)
	   private LocalDateTime createdAt;
	   
	   @Column(nullable = true, unique = false)
	   private LocalDateTime at;
	   
	   @ManyToOne(fetch = FetchType.EAGER)
	   @JoinColumn(name = "sentBy", referencedColumnName = "username")
	   private User sentBy;
	   
	   @ManyToOne(fetch = FetchType.EAGER)
	   @JoinColumn(name = "sentFor", referencedColumnName = "username")
	   private User sentFor;
	   
	   

	    public FriendRequest() {}
	    
		public FriendRequest(Long friendRequestId, boolean approved, LocalDateTime createdAt, LocalDateTime at
				,User sentBy,User sentFor) {
			super();
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

		public Boolean isApproved() {
			return approved;
		}

		public void setApproved(Boolean approved) {
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

		public String getSentBy() {
			return sentBy.getUsername();
		}

		
		public String getUserSentByInfo() {
			return this.sentBy.getFirstName()+" "+this.sentBy.getLastName();
		}
		public void setSentBy(User sentBy) {
			this.sentBy = sentBy;
		}

		public String getSentFor() {
			return sentFor.getUsername();
		}

		public void setSentFor(User sentFor) {
			this.sentFor = sentFor;
		}


}
