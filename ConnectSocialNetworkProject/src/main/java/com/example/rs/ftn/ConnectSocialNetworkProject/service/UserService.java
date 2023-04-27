package com.example.rs.ftn.ConnectSocialNetworkProject.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.rs.ftn.ConnectSocialNetworkProject.exception.UserNotFoundException;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.User;
import com.example.rs.ftn.ConnectSocialNetworkProject.repository.UserRepo;

@Service
public class UserService {
	
	 private final UserRepo userRepo; 
	    
	    @Autowired
	    public UserService(UserRepo userRepo) {
	    	this.userRepo = userRepo;
	    }
	    
	    public User findOne(String username) {
			return userRepo.findById(username).orElseThrow(() -> 
			new UserNotFoundException("User by id " + username + "was not found"));
		}

		public List<User> findAll() {
			return userRepo.findAll();
		}
		
		public Page<User> findAll(Pageable page) {
			return userRepo.findAll(page);
		}

		public void remove(String username) {
			userRepo.deleteById(username);
		}
		
		public User updateUser(User user) {
			return userRepo.save(user);
		}
		
		  public User addUser(User user) {
			   
		        if (userRepo.existsById(user.getUsername())) {
		            throw new IllegalArgumentException("Username " + user.getUsername() + " already exists");
		        }
		        return userRepo.save(user);

}


}
