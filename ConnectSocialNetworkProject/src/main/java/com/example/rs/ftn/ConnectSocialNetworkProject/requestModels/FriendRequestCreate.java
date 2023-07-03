package com.example.rs.ftn.ConnectSocialNetworkProject.requestModels;


public class FriendRequestCreate {
	
	public FriendRequestCreate() {}
	
	private String sentFor;

	public FriendRequestCreate(String sentFor) {
		super();
		this.sentFor = sentFor;
	}

	public String getSentFor() {
		return sentFor;
	}

	public void setSentFor(String sentFor) {
		this.sentFor = sentFor;
	}
	

}
