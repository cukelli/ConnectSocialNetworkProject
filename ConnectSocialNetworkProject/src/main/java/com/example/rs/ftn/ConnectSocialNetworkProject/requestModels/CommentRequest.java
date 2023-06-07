package com.example.rs.ftn.ConnectSocialNetworkProject.requestModels;

public class CommentRequest {
	
	private String text;
	
	public CommentRequest() {}
	

	public CommentRequest(String text) {
		super();
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	

}
