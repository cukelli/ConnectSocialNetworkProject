package com.example.rs.ftn.ConnectSocialNetworkProject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Reaction;

@Repository
public interface ReactionRepo extends JpaRepository<Reaction,Long> {
	

    public List<Reaction> findAll();
	
	public Page<Reaction> findAll(Pageable pageable);
	
	Reaction save (Reaction reaction);
	
    void delete(Reaction reaction);
	
	Optional<Reaction> findById(Long id); //optinal rukuje sa null vrednostima
	
	long count();

}
