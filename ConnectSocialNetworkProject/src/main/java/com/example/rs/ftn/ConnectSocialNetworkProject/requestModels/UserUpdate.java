package com.example.rs.ftn.ConnectSocialNetworkProject.requestModels;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserUpdate {
	
	
	@NotBlank
	@Pattern(regexp = "[a-zA-Z]+", message = "First name should contain only letters.")
	private String firstName;
	
	@NotBlank
	@Pattern(regexp = "[a-zA-Z]+", message = "Last name should contain only letters.")
	private String lastName;
	
	@NotBlank
	@Pattern(regexp = "[a-zA-Z0-9 ]+", message = "Username should contain only letters, numbers, and spaces.")
	private String username;
	
	@NotNull
	@Email
	private String email;
	
	public UserUpdate() {}
	
	
	public UserUpdate(String firstName, String lastName, String username, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
