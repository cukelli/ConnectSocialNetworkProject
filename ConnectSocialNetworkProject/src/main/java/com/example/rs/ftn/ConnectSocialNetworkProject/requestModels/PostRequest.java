package com.example.rs.ftn.ConnectSocialNetworkProject.requestModels;

import java.time.LocalDateTime;


public class PostRequest {
	   
	   private String content;
	   
	   private String image;
	   
	   private LocalDateTime creationDate;
	   	   
	   
	
		public PostRequest(String content,String image, LocalDateTime creationDate) {
			this.content = content;
			this.image = image;
			this.creationDate = creationDate;
		}



		public String getContent() {
			return content;
		}



		public void setContent(String content) {
			this.content = content;
		}



		public LocalDateTime getCreationDate() {
			return creationDate;
		}



		public void setCreationDate(LocalDateTime creationDate) {
			this.creationDate = creationDate;
		}



		public String getImage() {
			return image;
		}



		public void setImage(String image) {
			this.image = image;
		}
		

}
