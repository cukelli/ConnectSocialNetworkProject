package com.example.rs.ftn.ConnectSocialNetworkProject.model.entity;
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

@Entity
public class Post {
	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private Long postId;
	   
	   @Column(nullable = false,unique = false)
		   private String content;

	  @Column(nullable = false,unique = false)
	  private String title;
	   
	   @Column(nullable = false,unique = false)
	   private LocalDateTime creationDate;
	   
	   @Column(nullable = false,unique = false)
	   private boolean isDeleted;
	   
	   @OneToMany( mappedBy = "belongsTo")
	   @Column(nullable = true,unique = false)
	    private List<Image> imagePaths;
	   
	   @ManyToOne(fetch = FetchType.EAGER)
	   @JoinColumn(name = "userId",nullable = true)
	   private User user;
	   
	   @OneToMany(mappedBy = "commentedPost")
	   private List<Comment> comments;
	   
	   @OneToMany(mappedBy = "postReacted")
	   private List<Reaction> reactions;
	   
	   @OneToMany(mappedBy = "reportedPost")
	   private List<Report> reports;
	   
	   @ManyToOne
	   @JoinColumn(name = "groupId", nullable = true)
	   private Group groupPosted;
	   
	   public Post() {}

	public Post(Long postId, String content, LocalDateTime creationDate, User user,boolean isDeleted,String title) {
		super();
		this.postId = postId;
		this.content = content;
		this.creationDate = creationDate;
		//this.imagePaths = imagePaths;
		this.user = user;
		this.isDeleted = false;
		this.title = title;
	}

	public Long getId() {
		return postId;
	}

	public void setId(Long postId) {
		this.postId = postId;
	}

	public String getContent() {
		return content;
	}

	public String getTitle() {
		   return title;
	}

	public void setTitle(String title) {
		   this.title = title;
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

	public List<String> getImagePaths() {
		List<String> imagePathsR = new ArrayList<>();
		if  (this.imagePaths != null) {
			for (Image i: this.imagePaths) {
				imagePathsR.add(i.getPath());
			}
		}
		
		return imagePathsR;

	}

	public void setImagePaths(List<Image> imagePaths) {
		this.imagePaths = imagePaths;
	}

	public String getUser() {
		return user.getUsername();
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Reaction> getReactions() {
		return reactions;
	}

	public void setReactions(List<Reaction> reactions) {
		this.reactions = reactions;
	}

	public List<Report> getReports() {
		return reports;
	}

	public void setReports(List<Report> reports) {
		this.reports = reports;
	}

	public Long getGroupPosted() {
		if (this.groupPosted == null) {
			return (long) -1;
		}
		return groupPosted.getGroupId();
	}

	public void setGroupPosted(Group groupPosted) {
		this.groupPosted = groupPosted;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
