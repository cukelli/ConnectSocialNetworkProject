package com.example.rs.ftn.ConnectSocialNetworkProject.controller;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.example.rs.ftn.ConnectSocialNetworkProject.exception.PostNotFoundException;
import com.example.rs.ftn.ConnectSocialNetworkProject.exception.UserNotFoundException;
import com.example.rs.ftn.ConnectSocialNetworkProject.message.Message;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Comment;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Post;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Report;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.User;
import com.example.rs.ftn.ConnectSocialNetworkProject.requestModels.ReportRequest;
import com.example.rs.ftn.ConnectSocialNetworkProject.security.JwtUtil;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.CommentService;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.PostService;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.ReportService;
import com.example.rs.ftn.ConnectSocialNetworkProject.service.UserService;

@RestController
@RequestMapping("/report")
public class ReportController {
	
	private final ReportService reportService;
	private final UserService userService;
	private final PostService postService;
	private final CommentService commentService;
	private final JwtUtil jwtUtil;
	
	public ReportController(ReportService reportService, UserService userService,PostService postService,
			CommentService commentService,
			JwtUtil jwtUtil) {
		this.reportService = reportService;
		this.userService = userService;
		this.postService = postService;
		this.commentService = commentService;
		this.jwtUtil = jwtUtil;
	}
	
	
	@GetMapping("/all")
    @ResponseBody
    public List<Report> getAllReports(Authentication authentication) {
        String username = authentication.getName();
        User userLogged = null;
        try {
            userLogged = userService.findOne(username);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
        if (!userLogged.getRole().toString().equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not admin");
        }

        List<Report> reports;
        
        if (userLogged.getRole().toString().equals("ADMIN")) {
        	reports = reportService.findAll();
	    } else {
	    	reports = reportService.findAllUndeletedReports();
	    }

	    return reports;
    }
	
	@PostMapping("/reportPost/{postId}")
	public Object reportPost(Authentication authentication,@RequestBody ReportRequest report,
			@PathVariable("postId") Long postId) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		
		  Post post = null;
		    try {
		        post = postService.findOne(postId);
		    } catch (PostNotFoundException e) {
		        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found.");
		    }
		    
		    Report existingReport = reportService.findByReportedPostAndByUser(post, userLogged);
		    
		    if (existingReport != null) {
			    return new Message("You already reported this post.");	
		    }
		    
		
		Report newReport = new Report();
		newReport.setReportReason(report.getReportReason());
		newReport.setByUser(userLogged);
		newReport.setReportedPost(post);
		
		    return new ResponseEntity<>(newReport, HttpStatus.OK);
	}
	

	@PostMapping("/reportComment/{commentId}")
	public Object reportComment(Authentication authentication,@RequestBody ReportRequest report,
			@PathVariable("commentId") Long commentId) {
		String username = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		
		  Comment comment = null;
		    try {
		        comment = commentService.findOne(commentId);
		    } catch (PostNotFoundException e) {
		        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found.");
		    }
		    
		    Report existingReport = reportService.findByReportedCommentAndByUser(userLogged, comment);
		    
		    if (existingReport != null) {
			    return new Message("You already reported this comment.");	
		    }
		    
		
		Report newReport = new Report();
		newReport.setReportReason(report.getReportReason());
		newReport.setByUser(userLogged);
		newReport.setReportedComment(comment);
		
		    return new ResponseEntity<>(newReport, HttpStatus.OK);
	}
	
	
	@PostMapping("/reportUser/{username}")
	public Object reportComment(Authentication authentication,@RequestBody ReportRequest report,
			@PathVariable("username") String username) {
		String usernameUserLogged = authentication.getName();
		User userLogged = null;
		try {
			userLogged = userService.findOne(usernameUserLogged);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		
		  User reportedUser = null;
		    try {
		    	reportedUser = userService.findOne(username);
		    } catch (PostNotFoundException e) {
		        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		    }
		    
		    Report existingReport = reportService.findByReportedUserAndByUser(userLogged, reportedUser);
		    
		    if (existingReport != null) {
			    return new Message("You already reported this user.");	
		    }
		    
		
		Report newReport = new Report();
		newReport.setReportReason(report.getReportReason());
		newReport.setByUser(userLogged);
		newReport.setUser(reportedUser);
		
		    return new ResponseEntity<>(newReport, HttpStatus.OK);
	}
	
	
}
