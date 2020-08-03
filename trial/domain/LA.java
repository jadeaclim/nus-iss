package com.team1.iss.trial.domain;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "la")
public class LA implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int uid;
	private long fromTime;
	private long toTime;
	private String type;
	private String status;
	private String reasons;
	private String rejectReason;
	@ManyToOne
	@JoinColumn(name = "disseminationId")
	private Employee dissemination;
	private String contact;
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
	@JoinColumn(name = "ownerId")
	private Employee owner;
	@Transient
	private float duration;
	
	public LA() {
		super();
	}
	
	public LA(int uid) {
		super();
		this.uid = uid;
	}

	public LA(long fromTime, long toTime, String type, String status, String reasons, String rejectReason, Employee dissemination, String contact, Employee owner, float duration) {
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.type = type;
		this.status = status;
		this.reasons = reasons;
		this.rejectReason = rejectReason;
		this.dissemination = dissemination;
		this.contact = contact;
		this.owner = owner;
		this.duration = duration;
	}

	public LA(int uid, long fromTime, long toTime, String type, String status, String reasons, Employee dissemination,
			  String contact, Employee owner, String rejectReason) {
		super();
		this.uid = uid;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.type = type;
		this.status = status;
		this.reasons = reasons;
		this.dissemination = dissemination;
		this.contact = contact;
		this.owner = owner;
		this.rejectReason=rejectReason;
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public long getFromTime() {
		return fromTime;
	}

	public void setFromTime(long fromTime) {
		this.fromTime = fromTime;
	}

	public long getToTime() {
		return toTime;
	}

	public void setToTime(long toTime) {
		this.toTime = toTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReasons() {
		return reasons;
	}

	public void setReasons(String reasons) {
		this.reasons = reasons;
	}

	public Employee getDissemination() {
		return dissemination;
	}

	public void setDissemination(Employee dissemination) {
		this.dissemination = dissemination;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Employee getOwner() {
		return owner;
	}

	public void setOwner(Employee owner) {
		this.owner = owner;
	}


	@Override
	public String toString() {
		return "LA{" +
				"uid=" + uid +
				", fromTime=" + fromTime +
				", toTime=" + toTime +
				", type='" + type + '\'' +
				", status='" + status + '\'' +
				", reasons='" + reasons + '\'' +
				", rejectReason='" + rejectReason + '\'' +
				", dissemination=" + dissemination +
				", contact='" + contact + '\'' +
				", owner=" + owner +
				", duration=" + duration +
				'}';
	}
}
