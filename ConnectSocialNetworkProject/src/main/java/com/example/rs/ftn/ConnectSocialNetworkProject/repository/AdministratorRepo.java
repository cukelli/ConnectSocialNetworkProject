package com.example.rs.ftn.ConnectSocialNetworkProject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Administrator;

@Repository
public interface AdministratorRepo extends JpaRepository<Administrator,Long> {
	

    public List<Administrator> findAll();
	
	public Page<Administrator> findAll(Pageable pageable);
	
	Administrator save (Administrator administrator);
	
    void delete(Administrator administrator);
	
	Optional<Administrator> findById(Long id); //optinal rukuje sa null vrednostima
	
	long count();

}

