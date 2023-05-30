package com.example.rs.ftn.ConnectSocialNetworkProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Group;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.GroupRequest;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.User;

@Repository
public interface GroupRequestRepo extends JpaRepository<GroupRequest,Long> {
	
    GroupRequest findByUserAndGroup(User user, Group group);


}

