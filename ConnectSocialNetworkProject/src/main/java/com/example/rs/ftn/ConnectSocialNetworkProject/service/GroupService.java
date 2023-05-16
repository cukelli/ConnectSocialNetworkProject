package com.example.rs.ftn.ConnectSocialNetworkProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.rs.ftn.ConnectSocialNetworkProject.exception.GroupNotFoundException;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Group;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.GroupAdmin;
import com.example.rs.ftn.ConnectSocialNetworkProject.repository.GroupRepo;

@Service
public class GroupService {
	
	private final GroupRepo groupRepo;

    @Autowired
    public GroupService(GroupRepo groupRepo) {
    	this.groupRepo = groupRepo;
    }
    
    public Group findOne(Long id) {
		return groupRepo.findById(id).orElseThrow(() -> 
		new GroupNotFoundException("Group by id " + id + "was not found"));
	}

	public List<Group> findAll() {
		return groupRepo.findAll();
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
	  

}

