package com.example.rs.ftn.ConnectSocialNetworkProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.rs.ftn.ConnectSocialNetworkProject.exception.FriendRequestNotFoundException;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.FriendRequest;
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
	
}

