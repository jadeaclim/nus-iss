package com.team1.iss.trial.common;

public class CommConstants {
	
	public static final int MEDICAL_ALLOCATION=60;
	
	public static class UserType {
		public static final String AMDIN="ADMIN";
		public static final String EMPLOYEE="EMPLOYEE";
		public static final String MANAGER="MANAGER";
	}
	
	public static class LeaveType{
		public static final String ANNUAL_LEAVE="ANNUAL_LEAVE";
		public static final String MEDICAL_LEAVE="MEDICAL_LEAVE";
		public static final String COMPENSATION_LEAVE="COMPENSATION_LEAVE";
	}
	
	public static class ApplicationStatus{
		public static final String APPLIED="APPLIED";
		public static final String UPDATED="UPDATED";
		public static final String DELETED="DELETED";
		public static final String CANCELLED="CANCELLED";
		public static final String REJECTED="REJECTED";
		public static final String APPROVED="APPROVED";
	}
	
	public static class ClaimStatus{
		public static final String APPLIED="APPLIED";
		public static final String REJECTED="REJECTED";
		public static final String APPROVED="APPROVED";
	}
}
