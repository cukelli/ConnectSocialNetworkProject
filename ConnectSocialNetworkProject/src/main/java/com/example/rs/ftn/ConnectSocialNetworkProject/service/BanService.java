package com.example.rs.ftn.ConnectSocialNetworkProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.rs.ftn.ConnectSocialNetworkProject.exception.BanNotFoundException;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Ban;
import com.example.rs.ftn.ConnectSocialNetworkProject.repository.BanRepo;

@Service
public class BanService {
	
	private final BanRepo banRepo;
	
	 @Autowired
	    public BanService(BanRepo banRepo) {
	    	this.banRepo = banRepo;
	    }
	    
	    public Ban findOne(Long id) {
			return banRepo.findById(id).orElseThrow(() -> 
			new BanNotFoundException("Ban by id " + id + "was not found"));
		}

		public List<Ban> findAll() {
			return banRepo.findAll();
		}
		
		public Page<Ban> findAll(Pageable page) {
			return banRepo.findAll(page);
		}

		public void remove(Long id) {
			banRepo.deleteById(id);
		}
		
		public Ban updateBan(Ban Ban) {
			return banRepo.save(Ban);
		}
		

	 
		

}
