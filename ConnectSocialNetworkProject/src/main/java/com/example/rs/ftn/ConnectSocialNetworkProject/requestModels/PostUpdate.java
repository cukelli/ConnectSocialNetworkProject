package com.example.rs.ftn.ConnectSocialNetworkProject.requestModels;

import java.time.LocalDateTime;

public class PostUpdate {

		   private String content;
		   private Long postId;
		   	   
		   
			public PostUpdate(String content, Long postId) {
				this.content = content;
				this.postId = postId;
			}



			public String getContent() {
				return content;
			}



			public void setContent(String content) {
				this.content = content;
			}



			

			public Long getPostId() {
				return postId;
			}



			public void setPostId(Long postId) {
				this.postId = postId;
			}

}
