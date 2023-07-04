package com.example.rs.ftn.ConnectSocialNetworkProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.rs.ftn.ConnectSocialNetworkProject.exception.GroupRequestNotFoundException;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Group;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.GroupRequest;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.User;
import com.example.rs.ftn.ConnectSocialNetworkProject.repository.GroupRequestRepo;

@Service
public class GroupRequestService {
	
	private final GroupRequestRepo groupRequestRepo;
	
	 @Autowired
	    public GroupRequestService(GroupRequestRepo groupRequestRepo) {
	    	this.groupRequestRepo = groupRequestRepo;
	    }
	    
	    public GroupRequest findOne(Long id) {
			return groupRequestRepo.findById(id).orElseThrow(() -> 
			new GroupRequestNotFoundException("GroupRequest by id " + id + "was not found"));
		}

		public List<GroupRequest> findAll() {
			return groupRequestRepo.findAll();
		}
		
		public Page<GroupRequest> findAll(Pageable page) {
			return groupRequestRepo.findAll(page);
		}

		public void remove(Long id) {
			groupRequestRepo.deleteById(id);
		}
		
		public GroupRequest updateGroupRequest(GroupRequest GroupRequest) {
			return groupRequestRepo.save(GroupRequest);
		}
		  public GroupRequest addGroupRequest(GroupRequest groupRequest) {
			  
		        return groupRequestRepo.save(groupRequest);
	     }
	
		 public GroupRequest findByUserAndGroup(User user,Group group) {
			 return groupRequestRepo.findByUserAndGroup(user, group);
		 }
		 
		 public List<GroupRequest> findByUser(User user) {
			 return groupRequestRepo.findByUserAndApprovedTrue(user);
		 }
}

