package com.example.rs.ftn.ConnectSocialNetworkProject.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.rs.ftn.ConnectSocialNetworkProject.enumeration.ReportReason;

@Entity
public class Report {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long reportId;
	 
	 @Enumerated(EnumType.STRING)
	 private ReportReason reportReason;
	 
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "userReported")
	 private User byUser;
	 
	 @Column(nullable = false, unique = false)
	 private LocalDateTime timestamp;
	 
	 @ManyToOne(fetch = FetchType.EAGER)
	 @JoinColumn(name = "username", nullable = true)
	 private User user;
	 
	 @Column(nullable = false)
	 private boolean accepted;
	 
	 @Column(nullable = false, unique = false)
	 private boolean isDeleted;
	 
	 @ManyToOne
	 @JoinColumn(name = "postId", nullable = true)
	 private Post reportedPost;
	 
	 @ManyToOne
	 @JoinColumn(name = "commentId", nullable = true)
	 private Comment reportedComment;
	 
	 
	 public Report() {}

	public Report(Long reportId, ReportReason reportReason,User byUser, LocalDateTime timestamp, User user, boolean accepted, boolean isDeleted,
			Post reportedPost, Comment reportedComment) {
		super();
		this.reportId = reportId;
		this.reportReason = reportReason;
		this.byUser = byUser;
		this.timestamp = timestamp;
		this.user = user;
		this.accepted = accepted;
		this.isDeleted = isDeleted;
		this.reportedPost = reportedPost;
		this.reportedComment = reportedComment;
	}

	public Long getId() {
		return reportId;
	}

	public void setId(Long id) {
		this.reportId = id;
	}

	public ReportReason getReportReason() {
		return reportReason;
	}

	public void setReportReason(ReportReason reportReason) {
		this.reportReason = reportReason;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getUser() {
		return user.getUsername();
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public Long getReportedPost() {
		return reportedPost.getId();
	}

	public void setReportedPost(Post reportedPost) {
		this.reportedPost = reportedPost;
	}

	public Long getReportedComment() {
		return reportedComment.getId();
	}

	public void setReportedComment(Comment reportedComment) {
		this.reportedComment = reportedComment;
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getByUser() {
		return byUser.getUsername();
	}

	public void setByUser(User byUser) {
		this.byUser = byUser;
	}
	
	
}
