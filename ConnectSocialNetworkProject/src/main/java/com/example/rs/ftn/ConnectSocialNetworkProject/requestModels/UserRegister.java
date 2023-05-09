package com.example.rs.ftn.ConnectSocialNetworkProject.requestModels;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.rs.ftn.ConnectSocialNetworkProject.enumeration.Role;

public class UserRegister{
	
	private Role role;
	
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
	
	@NotBlank
    @Size(min = 8, message = "Password should be at least 8 characters long.")
	private String password;
	
    private PasswordEncoder passwordEncoder;

	
	public UserRegister(Role role,String firstName, String lastName, String username, String email, String password,
			PasswordEncoder passwordEncoder) {
		super();
		this.role = role;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = password;
	    this.passwordEncoder = passwordEncoder;

		
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		  this.password = password;
		  this.password = passwordEncoder.encode(password);
	}
	

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
}
