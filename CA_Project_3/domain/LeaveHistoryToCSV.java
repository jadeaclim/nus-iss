package com.team1.iss.trial.domain;

public class LeaveHistoryToCSV {

	
	private String LeaveID;
	private String Type;
	private String From;
	private String To;
	private String Reason;
	private String Status;
	private String NoOfDays;
	public LeaveHistoryToCSV() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getLeaveID() {
		return LeaveID;
	}
	public void setLeaveID(String leaveID) {
		LeaveID = leaveID;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getFrom() {
		return From;
	}
	public void setFrom(String from) {
		From = from;
	}
	public String getTo() {
		return To;
	}
	public void setTo(String to) {
		To = to;
	}
	public String getReason() {
		return Reason;
	}
	public void setReason(String reason) {
		Reason = reason;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getNoOfDays() {
		return NoOfDays;
	}
	public void setNoOfDays(String noOfDays) {
		NoOfDays = noOfDays;
	}

}
