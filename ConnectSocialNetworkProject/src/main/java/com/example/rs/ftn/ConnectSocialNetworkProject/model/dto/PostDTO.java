package com.example.rs.ftn.ConnectSocialNetworkProject.model.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Comment;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.User;

public class PostDTO {
	
	private Long postId;
	private String content;
	private ArrayList<String> imagePaths;
	private LocalDateTime creationDate;
	public ArrayList<Comment> comments;
	private User postedBy;
	
	public PostDTO() {}	
	
	public PostDTO(Long postId, String content, ArrayList<String> imagePaths,
			LocalDateTime creationDate,ArrayList<Comment> comments,User postedBy) {
		super();
		this.postId = postId;
		this.content = content;
		this.creationDate = creationDate;
		this.imagePaths = imagePaths;
		this.postedBy = postedBy;
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
	public ArrayList<String> getImagePaths() {
		return imagePaths;
	}
	public void setImagePaths(ArrayList<String> imagePaths) {
		this.imagePaths = imagePaths;
	}

	public User getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(User postedBy) {
		this.postedBy = postedBy;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public ArrayList<Comment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}


}
