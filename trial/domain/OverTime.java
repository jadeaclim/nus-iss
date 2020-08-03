package com.team1.iss.trial.domain;

import javax.persistence.*;

@Entity
public class OverTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int uid;
	@Column
	private long startTime;
	@Column
	private long endTime;
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
	@JoinColumn(name = "ownerId")
	private Employee owner;
	@Column
	private String status;
	@Transient
	private int hours;

	public OverTime(int uid, long startTime, long endTime, Employee owner, String status) {
		super();
		this.uid = uid;
		this.startTime = startTime;
		this.endTime = endTime;
		this.owner = owner;
		this.status = status;
	}

	public OverTime(long startTime, long endTime, Employee owner, String status, int hours) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.owner = owner;
		this.status = status;
		this.hours = hours;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public OverTime() {
		super();
	}

	public OverTime(int uid) {
		super();
		this.uid = uid;
	}
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Employee getOwner() {
		return owner;
	}

	public void setOwner(Employee owner) {
		this.owner = owner;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	

}
