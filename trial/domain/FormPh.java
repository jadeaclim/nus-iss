package com.team1.iss.trial.domain;

import javax.persistence.Column;

public class FormPh {
	
	
	private int uid;
	private String day;
	private String name;
	public FormPh() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FormPh(int uid, String day, String name) {
		super();
		this.uid = uid;
		this.day = day;
		this.name = name;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
