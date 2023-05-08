package com.example.rs.ftn.ConnectSocialNetworkProject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User,String> {
	
	void deleteUserByUsername(String username);

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByUsernameAndPassword(String username,String password);
    
    Optional<User> findUserByEmail(String email);
    

}
