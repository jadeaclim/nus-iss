package com.team1.iss.trial.domain;

import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.team1.iss.trial.common.CommConstants;

@Entity
@DiscriminatorValue(CommConstants.UserType.EMPLOYEE)
public class Employee extends User{

	@ManyToOne
	@JoinColumn(name = "managerId",insertable = false, updatable = false)
	private Manager manager;
	@OneToMany(mappedBy = "owner",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<LA> laList;
	
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Employee(int uid, String userType, String name, byte[] photo, String email, int annualLeaveEntitlement,
			int medicalLeaveEntitlement, boolean enabled, List<OverTime> overtimeList, Manager manager) {
		super(uid, userType, name, photo, email, annualLeaveEntitlement, medicalLeaveEntitlement, enabled, overtimeList,
				manager);
		// TODO Auto-generated constructor stub
		
	}



	public Employee(int uid, String userType, String name, byte[] photo, String password, String email,
			int annualLeaveEntitlement, int medicalLeaveEntitlement, boolean enabled, List<OverTime> overtimeList,
			Manager manager) {
		super(uid, userType, name, photo, password, email, annualLeaveEntitlement, medicalLeaveEntitlement, enabled,
				overtimeList, manager);
		// TODO Auto-generated constructor stub
	}






	public Employee(int uid, String userType, String name, String password, String email, int annualLeaveEntitlement,
			int medicalLeaveEntitlement, boolean enabled) {
		super(uid, userType, name, password, email, annualLeaveEntitlement, medicalLeaveEntitlement, enabled);
		// TODO Auto-generated constructor stub
	}






	public Employee(int uid) {
		super(uid);
		// TODO Auto-generated constructor stub
	}


	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public List<LA> getLaList() {
		return laList;
	}

	public void setLaList(List<LA> laList) {
		this.laList = laList;
	}

}
