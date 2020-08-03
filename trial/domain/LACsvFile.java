package com.team1.iss.trial.domain;


public class LACsvFile {
	
	private int LAuid;
	private String contact;
	private String fromTime;
	private String toTime;
	private String reasons;
	private String type;
	private String standin;
	private String ownername;
	private int ownerID;
	private String status;
	public LACsvFile() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getLAuid() {
		return LAuid;
	}
	public void setLAuid(int lAuid) {
		LAuid = lAuid;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getFromTime() {
		return fromTime;
	}
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}
	public String getToTime() {
		return toTime;
	}
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
	public String getReasons() {
		return reasons;
	}
	public void setReasons(String reasons) {
		this.reasons = reasons;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStandin() {
		return standin;
	}
	public void setStandin(String standin) {
		this.standin = standin;
	}
	public String getOwnername() {
		return ownername;
	}
	public void setOwnername(String ownername) {
		this.ownername = ownername;
	}
	public int getOwnerID() {
		return ownerID;
	}
	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	


}
