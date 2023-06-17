package com.example.rs.ftn.ConnectSocialNetworkProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.rs.ftn.ConnectSocialNetworkProject.exception.PostNotFoundException;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Group;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Post;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.User;
import com.example.rs.ftn.ConnectSocialNetworkProject.repository.PostRepo;

@Service
public class PostService {
	
private final PostRepo postRepo;
	
    @Autowired
    public PostService(PostRepo postRepo) {
    	this.postRepo = postRepo;
    }
    
    public Post findOne(Long id) {
		return postRepo.findById(id).orElseThrow(() -> 
		new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
	}

	public List<Post> findAll() {
		return postRepo.findAll();
	}
	
	public Page<Post> findAll(Pageable page) {
		return postRepo.findAll(page);
	}
	
	public List<Post> findAllUndeletedPosts() {
		return postRepo.findAllByIsDeletedFalse();
		
	}
	public void remove(Long id) {
		postRepo.deleteById(id);
	}
	
	public Post updatePost(Post Post) {
		return postRepo.save(Post);
	}
	
	 public Post addPost(Post post) {   
	       return postRepo.save(post);
    }
	 
	 public List<Post> getUserPosts(String username) {
		 return postRepo.findAllByIsDeletedFalseAndUserUsername(username);
	 }
	  

}
