package com.example.rs.ftn.ConnectSocialNetworkProject.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name = "images")
public class Image {
	@Column(length=5000, nullable = false, unique = false)
	private String path;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = false)
	private boolean isDeleted;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "username")
    private User postedImageBy;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "postId")
    private Post belongsTo;	
	
	public Image() {}

	public Image(String path, Long id) {
		super();
		this.path = path;
		this.id = id;
	}
	
	

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPostedImageBy() {
		return postedImageBy.getUsername();
	}

	public void setPostedImageBy(User postedImageBy) {
		this.postedImageBy = postedImageBy;
	}

	public Post getBelongsTo() {
		return belongsTo;
	}

	public void setBelongsTo(Post belongsTo) {
		this.belongsTo = belongsTo;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}	
	
}
