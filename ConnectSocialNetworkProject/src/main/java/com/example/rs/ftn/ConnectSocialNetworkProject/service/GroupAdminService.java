package com.example.rs.ftn.ConnectSocialNetworkProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.rs.ftn.ConnectSocialNetworkProject.exception.GroupAdminNotFoundException;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.GroupAdmin;
import com.example.rs.ftn.ConnectSocialNetworkProject.repository.GroupAdminRepo;

@Service
public class GroupAdminService {
	
	private final GroupAdminRepo groupAdminRepo;
	
	
    @Autowired
    public GroupAdminService(GroupAdminRepo groupAdminRepo) {
    	this.groupAdminRepo = groupAdminRepo;
    }
	
	 
    public GroupAdmin findOne(Long id) {
		return groupAdminRepo.findById(id).orElseThrow(() -> 
		new GroupAdminNotFoundException("GroupAdmin by id " + id + "was not found"));
	}

	public List<GroupAdmin> findAll() {
		return groupAdminRepo.findAll();
	}
	
	public Page<GroupAdmin> findAll(Pageable page) {
		return groupAdminRepo.findAll(page);
	}

	public void remove(Long id) {
		groupAdminRepo.deleteById(id);
	}
	
	public GroupAdmin updateGroupAdmin(GroupAdmin GroupAdmin) {
		return groupAdminRepo.save(GroupAdmin);
	}
	
	

}

