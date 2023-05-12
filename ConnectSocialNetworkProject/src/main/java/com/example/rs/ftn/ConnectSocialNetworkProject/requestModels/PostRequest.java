package com.example.rs.ftn.ConnectSocialNetworkProject.requestModels;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

public class PostRequest {
	   
	   private String content;
	   
	   private LocalDateTime creationDate;
	   	   
	   
	
		public PostRequest(String content, LocalDateTime creationDate) {
			this.content = content;
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


}
