package com.example.rs.ftn.ConnectSocialNetworkProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.rs.ftn.ConnectSocialNetworkProject.exception.ReportNotFoundException;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Comment;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Post;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Report;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.User;
import com.example.rs.ftn.ConnectSocialNetworkProject.repository.ReportRepo;

@Service
public class ReportService {
	
	private final ReportRepo reportRepo;
	
    @Autowired
    public ReportService(ReportRepo reportRepo) {
    	this.reportRepo = reportRepo;
    }
    
    public Report findOne(Long id) {
		return reportRepo.findById(id).orElseThrow(() -> 
		new ReportNotFoundException("Report by id " + id + "was not found"));
	}

	public List<Report> findAll() {
		return reportRepo.findAll();
	}
	
	public Page<Report> findAll(Pageable page) {
		return reportRepo.findAll(page);
	}

	public void remove(Long id) {
		reportRepo.deleteById(id);
	}
	
	public Report updateReport(Report Report) {
		return reportRepo.save(Report);
	}
	
    public List<Report> findAllUndeletedReports() {
    	return reportRepo.findAllByIsDeletedFalse();
    }
    
    public Report addReport(Report report) {   
	       return reportRepo.save(report);
 }
    
    public Report findByReportedPostAndByUser(Post post,User user) {
		return reportRepo.findByByUserAndReportedPost(user, post);
	 }
    
    public Report findByReportedUserAndByUser(User userReporting,User userReported) {
  		return reportRepo.findByByUserAndUser(userReporting, userReported);
  	 }
    
    public Report findByReportedCommentAndByUser(User user,Comment comment) {
  		return reportRepo.findByByUserAndReportedComment(comment, user);
  	 }
	     
}

