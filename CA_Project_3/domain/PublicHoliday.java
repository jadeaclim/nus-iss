package com.team1.iss.trial.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PublicHoliday implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int uid;
	@Column
	private long day;
	@Column
	private String name;
	
	
	public PublicHoliday(int uid, long day, String name) {
		super();
		this.uid = uid;
		this.day = day;
		this.name = name;
	}
	
	public PublicHoliday() {
		super();
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public long getDay() {
		return day;
	}
	public void setDay(long day) {
		this.day = day;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	

}
