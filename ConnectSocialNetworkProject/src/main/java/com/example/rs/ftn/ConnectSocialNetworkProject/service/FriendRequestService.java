package com.example.rs.ftn.ConnectSocialNetworkProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.rs.ftn.ConnectSocialNetworkProject.exception.FriendRequestNotFoundException;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.FriendRequest;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.GroupRequest;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.User;
import com.example.rs.ftn.ConnectSocialNetworkProject.repository.FriendRequestRepo;

@Service
public class FriendRequestService {
	
	private final FriendRequestRepo friendRequestRepo;
	
	 @Autowired
	    public FriendRequestService(FriendRequestRepo friendRequestRepo) {
	    	this.friendRequestRepo = friendRequestRepo;
	    }
	    
	    public FriendRequest findOne(Long id) {
			return friendRequestRepo.findById(id).orElseThrow(() -> 
			new FriendRequestNotFoundException("FriendRequest by id " + id + "was not found"));
		}

		public List<FriendRequest> findAll() {
			return friendRequestRepo.findAll();
		}
		
		public Page<FriendRequest> findAll(Pageable page) {
			return friendRequestRepo.findAll(page);
		}

		public void remove(Long id) {
			friendRequestRepo.deleteById(id);
		}
		
		public FriendRequest updateFriendRequest(FriendRequest FriendRequest) {
			return friendRequestRepo.save(FriendRequest);
		}
		
		public FriendRequest findBySentByAndSentFor(User sentBy, User sentFor) {
			return friendRequestRepo.findBySentByAndSentFor(sentBy, sentFor);
		}
		
		public FriendRequest addFriendRequest(FriendRequest friendRequest) {
			  
	        return friendRequestRepo.save(friendRequest);
     }
		
		public List<FriendRequest> findBySentFor(User sentFor) {
			return friendRequestRepo.findBySentFor(sentFor);
		}
		
		public List<FriendRequest> findBySentBy(User sentBy) {
			return friendRequestRepo.findBySentBy(sentBy);
		}
		
		public List<FriendRequest> findByApprovedFalse(User user) {
			return friendRequestRepo.findAllByApprovedFalseAndSentFor(user);
		}
		
		public List<FriendRequest> findAllByApprovedTrueAndSentBy(User sentBy) {
			return friendRequestRepo.findAllByApprovedTrueAndSentBy(sentBy);
		}
		
		public List<FriendRequest> findAllByApprovedTrueAndSentFor(User sentFor) {
			return friendRequestRepo.findAllByApprovedTrueAndSentFor(sentFor);
		}
		
}

