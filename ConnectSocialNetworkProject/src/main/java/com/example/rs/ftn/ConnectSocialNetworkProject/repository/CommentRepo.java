package com.example.rs.ftn.ConnectSocialNetworkProject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Comment;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Post;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Long>{

	public List<Comment> findAll();
	
	public Page<Comment> findAll(Pageable pageable);
	
	Comment save(Comment comment);
	
	void delete(Comment comment);
	
	Optional<Comment> findById(Long id); //optinal rukuje sa null vrednostima
	
	long count();
	
	Page<Comment> findAllByIsDeletedFalse(Pageable pageable);
	
	List<Comment> findAllByIsDeletedFalse();
	
	List<Comment> findAllByCommentedPostAndParentCommentIsNullAndIsDeletedFalse(Post commentedPost);
	
	List<Comment> findAllByCommentedPost(Post commentedPost);
	
    List<Comment> findAllByRepliesIsNotEmpty();
   
	
}

