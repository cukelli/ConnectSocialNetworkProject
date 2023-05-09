package com.example.rs.ftn.ConnectSocialNetworkProject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.rs.ftn.ConnectSocialNetworkProject.exception.InvalidPasswordException;
import com.example.rs.ftn.ConnectSocialNetworkProject.exception.UserNotFoundException;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.User;
import com.example.rs.ftn.ConnectSocialNetworkProject.repository.UserRepo;

@Service
public class UserService {
	
	
	 @Autowired
	 private PasswordEncoder passwordEncoder;

	 private final UserRepo userRepo; 
	    
	    @Autowired
	    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
	        this.passwordEncoder = passwordEncoder;
	    	this.userRepo = userRepo;
	    }
	    
	    public User findOne(String username) {
			return userRepo.findById(username).orElseThrow(() -> 
			new UserNotFoundException("User not found"));
		}
	    
	    public User findOneByEmail(String email) {
	    	return userRepo.findUserByEmail(email).orElseThrow(() -> 
			new UserNotFoundException("User not found"));
	    }
	    
	    public User findOneByUsernameAndPassword(String username,String password) {
	    	return userRepo.findUserByUsernameAndPassword(username, password).orElseThrow(() -> 
	    	new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
	    	
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
		  
		  public User changePassword(String username, String oldPassword, String newPassword, String newPasswordRepeated) throws UserNotFoundException, InvalidPasswordException {
			  Optional<User> userOptional = userRepo.findUserByUsername(username);
			   User user = userOptional.orElseThrow(() -> new UserNotFoundException("User with username " + username + " not found."));
		            
		        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
		            throw new InvalidPasswordException("Old password does not match.");
		        }
		        
		        if (!newPassword.equals(newPasswordRepeated)) {
		            throw new InvalidPasswordException("New passwords do not match.");
		        }
		        
		        user.setPassword(passwordEncoder.encode(newPassword));
		        return userRepo.save(user);
		    }
		  
		  public boolean checkPassword(User user, String password) {
			    String encodedPassword = user.getPassword(); //proverava da li se stara lozinka poklapa
			    return passwordEncoder.matches(password, encodedPassword);
			}

      }
