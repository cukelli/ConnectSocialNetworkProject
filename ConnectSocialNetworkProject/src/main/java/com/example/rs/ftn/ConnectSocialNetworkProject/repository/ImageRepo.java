package com.example.rs.ftn.ConnectSocialNetworkProject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Image;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Post;

@Repository
public interface ImageRepo extends JpaRepository<Image,Long>  {
	
	   public List<Image> findAll();
		
		public Page<Image> findAll(Pageable pageable);
		
		Image save (Image image);
		
	    void delete(Image image);
		
		Optional<Image> findById(Long id); //optinal rukuje sa null vrednostima
		
		long count();
		
		List<Image> findAllByIsDeletedFalse();
		
		List<Image> findAllByBelongsTo(Post post);
		


}
