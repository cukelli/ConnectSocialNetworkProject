package com.example.rs.ftn.ConnectSocialNetworkProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.rs.ftn.ConnectSocialNetworkProject.exception.AdministratorNotFoundException;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Administrator;
import com.example.rs.ftn.ConnectSocialNetworkProject.repository.AdministratorRepo;

@Service
public class AdministratorService {
	
	private final AdministratorRepo administratorRepo;
	
	 @Autowired
	    public AdministratorService(AdministratorRepo administratorRepo) {
	    	this.administratorRepo = administratorRepo;
	    }
	    
	    public Administrator findOne(Long id) {
			return administratorRepo.findById(id).orElseThrow(() -> 
			new AdministratorNotFoundException("Administrator by id " + id + "was not found"));
		}

		public List<Administrator> findAll() {
			return administratorRepo.findAll();
		}
		
		public Page<Administrator> findAll(Pageable page) {
			return administratorRepo.findAll(page);
		}

		public void remove(Long id) {
			administratorRepo.deleteById(id);
		}
		
		public Administrator updateAdministrator(Administrator Administrator) {
			return administratorRepo.save(Administrator);
		}
}
