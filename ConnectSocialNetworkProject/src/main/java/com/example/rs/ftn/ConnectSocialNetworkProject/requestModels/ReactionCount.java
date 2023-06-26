package com.example.rs.ftn.ConnectSocialNetworkProject.requestModels;

public class ReactionCount {
	
	private long hearts;
	private long dislike;
	private long like;
	
	public ReactionCount(long hearts, long dislike, long like) {
		super();
		this.hearts = hearts;
		this.dislike = dislike;
		this.like = like;
	}

	public long getHearts() {
		return hearts;
	}

	public void setHearts(long hearts) {
		this.hearts = hearts;
	}

	public long getDislike() {
		return dislike;
	}

	public void setDislike(long dislike) {
		this.dislike = dislike;
	}

	public long getLike() {
		return like;
	}

	public void setLike(long like) {
		this.like = like;
	}
		
}
