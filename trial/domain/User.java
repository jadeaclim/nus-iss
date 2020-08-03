package com.team1.iss.trial.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name = "user")
@DiscriminatorColumn(name = "userType",discriminatorType = DiscriminatorType.STRING, length = 10)
public class User implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int uid;
	@Column(insertable = false, updatable = false)
	private String userType;
	@Column
	private String name;
	@Column
	private byte[] photo;
	@Column
	private String password;
	@Column
	private String email;
	@Column
	private int annualLeaveEntitlement;
	@Column
	private int medicalLeaveEntitlement;
	@Column
	private boolean enabled;
	@OneToMany(mappedBy = "owner",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<OverTime> overtimeList;
	@ManyToOne
	@JoinColumn(name = "managerId",insertable = false, updatable = false)
	private Manager manager;
	
	public User() {
		super();
	}
	
	public User(int uid) {
		super();
		this.uid = uid;
	}
	

	public User(int uid, String userType, String name, byte[] photo, String email, int annualLeaveEntitlement,
			int medicalLeaveEntitlement, boolean enabled, List<OverTime> overtimeList, Manager manager) {
		super();
		this.uid = uid;
		this.userType = userType;
		this.name = name;
		this.photo = photo;
		this.email = email;
		this.annualLeaveEntitlement = annualLeaveEntitlement;
		this.medicalLeaveEntitlement = medicalLeaveEntitlement;
		this.enabled = enabled;
		this.overtimeList = overtimeList;
		this.manager = manager;
	}

	public User(int uid, String userType, String name, byte[] photo, String password, String email,
			int annualLeaveEntitlement, int medicalLeaveEntitlement, boolean enabled, List<OverTime> overtimeList,
			Manager manager) {
		super();
		this.uid = uid;
		this.userType = userType;
		this.name = name;
		this.photo = photo;
		this.password = password;
		this.email = email;
		this.annualLeaveEntitlement = annualLeaveEntitlement;
		this.medicalLeaveEntitlement = medicalLeaveEntitlement;
		this.enabled = enabled;
		this.overtimeList = overtimeList;
		this.manager = manager;
	}
	
	

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public User(int uid, String userType, String name, String password, String email, int annualLeaveEntitlement,
			int medicalLeaveEntitlement, boolean enabled) {
		super();
		this.uid = uid;
		this.userType = userType;
		this.name = name;
		this.password = password;
		this.email = email;
		this.annualLeaveEntitlement = annualLeaveEntitlement;
		this.medicalLeaveEntitlement = medicalLeaveEntitlement;
		this.enabled = enabled;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public int getMedicalLeaveEntitlement() {
		return medicalLeaveEntitlement;
	}

	public void setMedicalLeaveEntitlement(int medicalLeaveEntitlement) {
		this.medicalLeaveEntitlement = medicalLeaveEntitlement;
	}

	public List<OverTime> getOvertimeList() {
		return overtimeList;
	}

	public void setOvertimeList(List<OverTime> overtimeList) {
		this.overtimeList = overtimeList;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getAnnualLeaveEntitlement() {
		return annualLeaveEntitlement;
	}

	public void setAnnualLeaveEntitlement(int annualLeaveEntitlement) {
		this.annualLeaveEntitlement = annualLeaveEntitlement;
	}

	public int getUid() {
		return uid;
	}
	
	public void setUid(int uid) {
		this.uid = uid;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	public byte[] getPhoto() {
		return photo;
	}
	
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
