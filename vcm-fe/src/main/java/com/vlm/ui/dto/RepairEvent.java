package com.vlm.ui.dto;

import java.util.Date;

public class RepairEvent {

	private String chasisNumber;
	private String dealerName;
	private String jobId;
	private String jobDescription;
	private String workDone;
	private int totalDriven;
	private Date date;
	
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getChasisNumber() {
		return chasisNumber;
	}
	public void setChasisNumber(String chasisNumber) {
		this.chasisNumber = chasisNumber;
	}
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getJobDescription() {
		return jobDescription;
	}
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
	public String getWorkDone() {
		return workDone;
	}
	public void setWorkDone(String workDone) {
		this.workDone = workDone;
	}
	public int getTotalDriven() {
		return totalDriven;
	}
	public void setTotalDriven(int totalDriven) {
		this.totalDriven = totalDriven;
	}

	
}
