package com.example.rs.ftn.ConnectSocialNetworkProject.requestModels;

public class JwtReturn {

	private String token;
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public JwtReturn(String token) {
		this.token=token;
	}
	
}
