package com.example.rs.ftn.ConnectSocialNetworkProject.repository;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Group;

@Repository
public interface GroupRepo extends JpaRepository<Group,Long> {
	
	    public List<Group> findAll();
		
		public Page<Group> findAll(Pageable pageable);
		
		List<Group> findAllByIsDeletedFalse();

		Page<Group> findAllByIsDeletedFalse(Pageable pageable);	
		
		
		
}
