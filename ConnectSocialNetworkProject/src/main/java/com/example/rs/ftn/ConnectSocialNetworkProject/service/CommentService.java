package com.example.rs.ftn.ConnectSocialNetworkProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.rs.ftn.ConnectSocialNetworkProject.exception.CommentNotFoundException;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Comment;
import com.example.rs.ftn.ConnectSocialNetworkProject.repository.CommentRepo;

@Service
public class CommentService {
	
	private final CommentRepo commentRepo;
	
	 @Autowired
	    public CommentService(CommentRepo commentRepo) {
	    	this.commentRepo = commentRepo;
	    }
	    
	    public Comment findOne(Long id) {
			return commentRepo.findById(id).orElseThrow(() -> 
			new CommentNotFoundException("Comment by id " + id + "was not found"));
		}

		public List<Comment> findAll() {
			return commentRepo.findAll();
		}
		
		public Page<Comment> findAll(Pageable page) {
			return commentRepo.findAll(page);
		}

		public void remove(Long id) {
			commentRepo.deleteById(id);
		}
		
		public Comment updateComment(Comment Comment) {
			return commentRepo.save(Comment);
		}
	  
}
