package com.example.rs.ftn.ConnectSocialNetworkProject.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Post {
	  @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private Long postId;
	   
	   @Column(nullable = false,unique = false)
	   private String content;
	   
	   @Column(nullable = false,unique = false)
	   private LocalDateTime creationDate;
	   
	   @Column(nullable = true,unique = false)
	   private ArrayList<String> imagePaths;
	   
	   @ManyToOne(fetch = FetchType.LAZY)
	   @JoinColumn(name = "userId",nullable = true)
	   private User user;
	   
	   @OneToMany(mappedBy = "commentedPost")
	   private List<Comment> comments;
	   
	   @OneToMany(mappedBy = "postReacted")
	   private List<Reaction> reactions;
	   
	   @OneToMany(mappedBy = "reportedPost")
	   private List<Report> reports;
	   
	   @ManyToOne
	   @JoinColumn(name = "groupId", nullable = false)
	   private Group groupPosted;
	   
	   public Post() {}

	public Post(Long postId, String content, LocalDateTime creationDate, ArrayList<String> imagePaths, User user) {
		super();
		this.postId = postId;
		this.content = content;
		this.creationDate = creationDate;
		this.imagePaths = imagePaths;
		this.user = user;
	}

	public Long getId() {
		return postId;
	}

	public void setId(Long postId) {
		this.postId = postId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public ArrayList<String> getImagePaths() {
		return imagePaths;
	}

	public void setImagePaths(ArrayList<String> imagePaths) {
		this.imagePaths = imagePaths;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


}
