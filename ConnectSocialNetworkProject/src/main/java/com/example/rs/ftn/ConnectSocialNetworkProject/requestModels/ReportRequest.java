package com.example.rs.ftn.ConnectSocialNetworkProject.requestModels;

import com.example.rs.ftn.ConnectSocialNetworkProject.enumeration.ReportReason;

public class ReportRequest {
	
	 private ReportReason reportReason;
	 	 
	 public ReportRequest() {}
	 
	 public ReportRequest(ReportReason reportReason) {
		 this.reportReason = reportReason;
	 }

	public ReportReason getReportReason() {
		return reportReason;
	}

	public void setReportReason(ReportReason reportReason) {
		this.reportReason = reportReason;
	}
	 
}
