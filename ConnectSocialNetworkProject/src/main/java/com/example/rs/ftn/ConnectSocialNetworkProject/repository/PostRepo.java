package com.example.rs.ftn.ConnectSocialNetworkProject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Post;

@Repository
public interface PostRepo extends JpaRepository<Post,Long> {
	
	public List<Post> findAll();

	public Page<Post> findAll(Pageable pageable);
	
	public List<Post> findByUserUsername(String username);

}

