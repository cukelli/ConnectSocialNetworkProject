package com.example.rs.ftn.ConnectSocialNetworkProject.model.dto;

import java.time.LocalDateTime;

import com.example.rs.ftn.ConnectSocialNetworkProject.enumeration.Role;

public class UserDTO {
	
	private Role role;
	private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String profileImagePath;
    private LocalDateTime lastLogin;

    
    public UserDTO() {}

	
	 public UserDTO(Role role,String username, String password, String email,
             String firstName, String lastName, String profileImagePath, LocalDateTime lastLogin) {
  this.role = role;
  this.username = username;
  this.password = password;
  this.email = email;
  this.firstName = firstName;
  this.lastName = lastName;
  this.profileImagePath = profileImagePath;
  this.lastLogin = lastLogin;
}


public String getUsername() {
  return username;
}

public void setUsername(String username) {
  this.username = username;
}

public String getPassword() {
  return password;
}

public void setPassword(String password) {
  this.password = password;
}

public String getEmail() {
  return email;
}

public void setEmail(String email) {
  this.email = email;
}

public String getFirstName() {
  return firstName;
}

public void setFirstName(String firstName) {
  this.firstName = firstName;
}

public String getLastName() {
  return lastName;
}

public void setLastName(String lastName) {
  this.lastName = lastName;
}

public String getProfileImagePath() {
  return profileImagePath;
}

public void setProfileImagePath(String profileImagePath) {
  this.profileImagePath = profileImagePath;
}

public LocalDateTime getLastLogin() {
  return lastLogin;
}

public void setLastLogin(LocalDateTime lastLogin) {
  this.lastLogin = lastLogin;
}


public Role getRole() {
	return role;
}


public void setRole(Role role) {
	this.role = role;
}

}
