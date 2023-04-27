package com.example.rs.ftn.ConnectSocialNetworkProject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Ban;

@Repository
public interface BanRepo extends JpaRepository<Ban,Long>{
	
    public List<Ban> findAll();
	
	public Page<Ban> findAll(Pageable pageable);
	
	Ban save (Ban ban);
	
    void delete(Ban ban);
	
	Optional<Ban> findById(Long id); //optinal rukuje sa null vrednostima
	
	long count();

}
