package com.example.rs.ftn.ConnectSocialNetworkProject.requestModels;

public class AnswerFriendRequest {
	
	private boolean approved;
	
	public AnswerFriendRequest() {}

	public AnswerFriendRequest(boolean approved) {
		super();
		this.approved = approved;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
	


}
