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
	 
	 @Column(nullable = false, unique = false)
	 private LocalDateTime timestamp;
	 
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "username")
	 private User user;
	 
	 @Column(nullable = false)
	 private boolean accepted;
	 
	 @Column(nullable = false, unique = false)
	 private boolean isDeleted;
	 
	 @ManyToOne
	 @JoinColumn(name = "postId")
	 private Post reportedPost;
	 
	 @ManyToOne
	 @JoinColumn(name = "commentId")
	 private Comment reportedComment;
	 
	 public Report() {}

	public Report(Long reportId, ReportReason reportReason, LocalDateTime timestamp, User user, boolean accepted, boolean isDeleted,
			Post reportedPost, Comment reportedComment) {
		super();
		this.reportId = reportId;
		this.reportReason = reportReason;
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

	public User getUser() {
		return user;
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

	public Post getReportedPost() {
		return reportedPost;
	}

	public void setReportedPost(Post reportedPost) {
		this.reportedPost = reportedPost;
	}

	public Comment getReportedComment() {
		return reportedComment;
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
	
}
