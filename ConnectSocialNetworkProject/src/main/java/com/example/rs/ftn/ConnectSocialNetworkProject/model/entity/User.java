package com.example.rs.ftn.ConnectSocialNetworkProject.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.rs.ftn.ConnectSocialNetworkProject.enumeration.Role;

@Entity
@Table (name = "users")
public class User {
	   @Id
	   @Column(nullable = false, unique = true)
	   private String username;
	   
	   @Column(nullable = false)
	   @Enumerated(EnumType.STRING)
	   private Role role;
	   
	   @Column(nullable = false, unique = false)
	   private String password;
	   
	   @Column(nullable = false, unique = true)
	   private String email;
	   
	   @Column(nullable = false, unique = false)
	   private String firstName;
	   
	   @Column(nullable = false, unique = false)
	   private String lastName;
	   
	   @Column(nullable = true, unique = false)
	   private String description;
	   
	   @Column(nullable = false, unique = false)
	   private String user_type;
	   
	   @Column(nullable = false, unique = false)
	   private boolean isDeleted;
	   
	   @OneToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "profile_image_id")
	    private Image profileImage;
	   
	   @Column(nullable = false, unique = false)
	   private LocalDateTime lastLogin;
	   
	   @OneToMany(mappedBy = "userCommented")
	   private List<Comment> comments;
	   
	   @OneToMany(mappedBy = "sentBy")
	   private List<FriendRequest> sentRequests;

	   @OneToMany(mappedBy = "sentFor")
	   private List<FriendRequest> receivedRequests;
	   
	   @OneToMany(mappedBy = "userReacted")
	   private List<Reaction> reactions;
	   
	   
		@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
		private List<Post> posts;
		
		@OneToMany(mappedBy = "bannedUser")
	    private List<Ban> bans;
	   
	   public User() {}

	public User(Role role,String username, String password, String email, String firstName, String lastName,String description,
			Image profileImage, LocalDateTime lastLogin, boolean isDeleted) {
		super();
		this.role = role;
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
		this.profileImage = profileImage;
		this.lastLogin = lastLogin;
		this.user_type="user";
		this.isDeleted = isDeleted;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getImage() {
		if (this.profileImage != null) {
		return this.profileImage.getPath();
		}
		else {
			return "";
		}
	}

	public void setProfileImage(Image profileImage) {
		this.profileImage = profileImage;
	}

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}


	public List<FriendRequest> getSentRequests() {
		return sentRequests;
	}

	public void setSentRequests(List<FriendRequest> sentRequests) {
		this.sentRequests = sentRequests;
	}

	public List<FriendRequest> getReceivedRequests() {
		return receivedRequests;
	}

	public void setReceivedRequests(List<FriendRequest> receivedRequests) {
		this.receivedRequests = receivedRequests;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Reaction> getReactions() {
		return reactions;
	}

	public void setReactions(List<Reaction> reactions) {
		this.reactions = reactions;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Ban> getBans() {
		return bans;
	}

	public void setBans(List<Ban> bans) {
		this.bans = bans;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
