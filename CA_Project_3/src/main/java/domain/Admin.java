package com.team1.iss.trial.domain;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.team1.iss.trial.common.CommConstants;

@Entity
@DiscriminatorValue(CommConstants.UserType.AMDIN)
public class Admin extends Employee{

	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Admin(int uid) {
		super(uid);
		// TODO Auto-generated constructor stub
	}

	
	public Admin(int uid, String userType, String name, byte[] photo, String email, int annualLeaveEntitlement,
			int medicalLeaveEntitlement, boolean enabled, List<OverTime> overtimeList, Manager manager) {
		super(uid, userType, name, photo, email, annualLeaveEntitlement, medicalLeaveEntitlement, enabled, overtimeList,
				manager);
		// TODO Auto-generated constructor stub
	}

	public Admin(int uid, String userType, String name, byte[] photo, String password, String email,
			int annualLeaveEntitlement, int medicalLeaveEntitlement, boolean enabled, List<OverTime> overtimeList,
			Manager manager) {
		super(uid, userType, name, photo, password, email, annualLeaveEntitlement, medicalLeaveEntitlement, enabled,
				overtimeList, manager);
		// TODO Auto-generated constructor stub
	}

	public Admin(int uid, String userType, String name, String password, String email, int annualLeaveEntitlement,
			int medicalLeaveEntitlement, boolean enabled) {
		super(uid, userType, name, password, email, annualLeaveEntitlement, medicalLeaveEntitlement, enabled);
		// TODO Auto-generated constructor stub
	}

	
}
