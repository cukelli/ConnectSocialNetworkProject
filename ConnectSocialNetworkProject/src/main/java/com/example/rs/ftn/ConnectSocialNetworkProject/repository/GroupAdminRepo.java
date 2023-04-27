package com.example.rs.ftn.ConnectSocialNetworkProject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.GroupAdmin;

@Repository
public interface GroupAdminRepo extends JpaRepository<GroupAdmin,Long> {
	
	public List<GroupAdmin> findAll();
	
	public Page<GroupAdmin> findAll(Pageable pageable);

	
	

}

