package com.example.rs.ftn.ConnectSocialNetworkProject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Comment;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Post;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Reaction;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Report;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.User;

@Repository
public interface ReportRepo extends JpaRepository<Report,Long> {
	
    public List<Report> findAll();
	
	public Page<Report> findAll(Pageable pageable);
	
	Report save (Report Report);
		
    void delete(Report Report);
	
	Optional<Report> findById(Long id); //optinal rukuje sa null vrednostima
	
	long count();
	
	List<Report> findAllByIsDeletedFalse();
	
	 Report findByByUserAndReportedPost(User user,Post post);
	    
	 Report findByByUserAndReportedComment(Comment comment,User user);
	 
	 Report findByByUserAndUser(User user,User userReported);




}

