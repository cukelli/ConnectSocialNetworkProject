package com.example.rs.ftn.ConnectSocialNetworkProject.requestModels;

import com.example.rs.ftn.ConnectSocialNetworkProject.enumeration.ReactionType;

public class ReactionRequest {
	
	private ReactionType type;
	
	public ReactionRequest() {}

	public ReactionRequest(ReactionType type) {
		super();
		this.type = type;
	}

	public ReactionType getType() {
		return type;
	}

	public void setType(ReactionType type) {
		this.type = type;
	}
	
	

}
