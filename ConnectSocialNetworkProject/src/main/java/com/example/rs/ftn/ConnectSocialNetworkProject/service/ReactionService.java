package com.example.rs.ftn.ConnectSocialNetworkProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.rs.ftn.ConnectSocialNetworkProject.enumeration.ReactionType;
import com.example.rs.ftn.ConnectSocialNetworkProject.exception.ReactionNotFoundException;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Comment;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Post;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Reaction;
import com.example.rs.ftn.ConnectSocialNetworkProject.repository.ReactionRepo;

@Service
public class ReactionService {
	
	private final ReactionRepo reactionRepo;
	
	 @Autowired
	    public ReactionService(ReactionRepo reactionRepo) {
	    	this.reactionRepo = reactionRepo;
	    }
	    
	    public Reaction findOne(Long id) {
			return reactionRepo.findById(id).orElseThrow(() -> 
			new ReactionNotFoundException("Reaction by id " + id + "was not found"));
		}

		public List<Reaction> findAll() {
			return reactionRepo.findAll();
		}
		
		public Page<Reaction> findAll(Pageable page) {
			return reactionRepo.findAll(page);
		}

		public void remove(Long id) {
			reactionRepo.deleteById(id);
		}
		
		public Reaction updateReaction(Reaction Reaction) {
			return reactionRepo.save(Reaction);
		}
		
		public List<Reaction> findAllUndeletedReactions() {
			return reactionRepo.findAllByIsDeletedFalse();
		}
		

		 public Reaction addReaction(Reaction reaction) {   
		       return reactionRepo.save(reaction);
	    }
		 
		 public long countByCommentReactedToAndType(Comment comment, ReactionType reactionType) {
			 return reactionRepo.countByCommentReactedToAndType(comment, reactionType);
		 }
		 
		 public long countByPostReactedAndType(Post post, ReactionType reactionType) {
			 return reactionRepo.countByPostReactedAndType(post, reactionType);
		 }
	    
}


