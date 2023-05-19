package com.example.rs.ftn.ConnectSocialNetworkProject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.rs.ftn.ConnectSocialNetworkProject.exception.GroupNotFoundException;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Group;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.User;
import com.example.rs.ftn.ConnectSocialNetworkProject.repository.GroupRepo;
import com.example.rs.ftn.ConnectSocialNetworkProject.repository.UserRepo;

@Service
public class GroupService {
	
	private final GroupRepo groupRepo;
	private final UserRepo userRepo;

    @Autowired
    public GroupService(GroupRepo groupRepo,UserRepo userRepo) {
    	this.groupRepo = groupRepo;
    	this.userRepo = userRepo;
    }
    
    public Group findOne(Long id) {
		return groupRepo.findById(id).orElseThrow(() -> 
		new GroupNotFoundException("Group by id " + id + "was not found"));
	}

	public List<Group> findAll() {
		return groupRepo.findAll();
	}
	
	public List<Group> findAllUndeletedGroups() {
		return groupRepo.findAllByIsDeletedFalse();
		
	}
	
	public Page<Group> findAll(Pageable page) {
		return groupRepo.findAll(page);
	}

	public void remove(Long id) {
		groupRepo.deleteById(id);
	}
	
	public Group updateGroup(Group Group) {
		return groupRepo.save(Group);
	}
	
	  public Group addGroup(Group group) {
		  
	        return groupRepo.save(group);
     }
	  
//	  public boolean isUserAdminOrMemberOfGroup(String username, Long groupId) {
//		    Optional<User> userOptional = userRepo.findById(username);
//		    Optional<Group> groupOptional = groupRepo.findById(groupId);
//
//		    if (userOptional.isPresent() && groupOptional.isPresent()) {
//		        User user = userOptional.get();
//		        Group group = groupOptional.get();
//
//		        boolean isAdmin = group.getAdmins().stream()
//		            .anyMatch(admin -> admin.getUser().equals(user));
//
//		        boolean isMember = group.getGroupRequests().stream()
//		            .filter(request -> request.isApproved() && request.getUser().equals(user))
//		            .findFirst()
//		            .isPresent();
//
//		        return isAdmin || isMember;
//		    }
//
//		    return false;
//		}
}

