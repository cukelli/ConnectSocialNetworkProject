package com.example.rs.ftn.ConnectSocialNetworkProject.model.dto;

import java.time.LocalDateTime;

import com.example.rs.ftn.ConnectSocialNetworkProject.enumeration.ReportReason;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Comment;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Post;

public class ReportDTO {

	private Long id;
    private ReportReason reportReason;
    private LocalDateTime timestamp;
    private UserDTO userDto;
    private boolean accepted;
    private Post reportedPost;
    private Comment reportedComment;

    public ReportDTO() {}

    public ReportDTO(Long id, ReportReason reportReason, LocalDateTime timestamp, UserDTO userDto, boolean accepted,
    		Post reportedPost,Comment reportedComment) {
        this.id = id;
        this.reportReason = reportReason;
        this.timestamp = timestamp;
        this.userDto = userDto;
        this.accepted = accepted;
        this.reportedPost = reportedPost;
        this.reportedComment = reportedComment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserDTO getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDTO userDto) {
        this.userDto = userDto;
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
    

}
