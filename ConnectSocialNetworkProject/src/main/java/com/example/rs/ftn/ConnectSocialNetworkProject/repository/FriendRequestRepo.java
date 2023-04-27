package com.example.rs.ftn.ConnectSocialNetworkProject.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.FriendRequest;

public interface FriendRequestRepo extends JpaRepository<FriendRequest,Long> {
	

	public Page<FriendRequest> findAll(Pageable pageable);
	
	FriendRequest save (FriendRequest friendRequest);
	
    void delete(FriendRequest friendRequest);
	
	Optional<FriendRequest> findById(Long id); //optinal rukuje sa null vrednostima
	
	long count();

}
