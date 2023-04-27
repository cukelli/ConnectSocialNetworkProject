package com.example.rs.ftn.ConnectSocialNetworkProject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Group;

@Repository
public interface GroupRepo extends JpaRepository<Group,Long> {
	
	    public List<Group> findAll();
		
		public Page<Group> findAll(Pageable pageable);
		
		Group save (Group group);
		
	    void delete(Group group);
		
		Optional<Group> findById(Long id); //optinal rukuje sa null vrednostima
		
		long count();

}
