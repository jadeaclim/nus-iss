package com.team1.iss.trial.domain;

import java.util.List;

public class FormEditUser extends User {
	private String ManagerName;



	public FormEditUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FormEditUser(int uid, String userType, String name, byte[] photo, String email, int annualLeaveEntitlement,
			int medicalLeaveEntitlement, boolean enabled, List<OverTime> overtimeList, Manager manager) {
		super(uid, userType, name, photo, email, annualLeaveEntitlement, medicalLeaveEntitlement, enabled, overtimeList,
				manager);
		// TODO Auto-generated constructor stub
	}

	public FormEditUser(int uid, String userType, String name, byte[] photo, String password, String email,
			int annualLeaveEntitlement, int medicalLeaveEntitlement, boolean enabled, List<OverTime> overtimeList,
			Manager manager) {
		super(uid, userType, name, photo, password, email, annualLeaveEntitlement, medicalLeaveEntitlement, enabled,
				overtimeList, manager);
		// TODO Auto-generated constructor stub
	}

	public FormEditUser(int uid, String userType, String name, String password, String email, int annualLeaveEntitlement,
			int medicalLeaveEntitlement, boolean enabled) {
		super(uid, userType, name, password, email, annualLeaveEntitlement, medicalLeaveEntitlement, enabled);
		// TODO Auto-generated constructor stub
	}

	public FormEditUser(int uid) {
		super(uid);
		// TODO Auto-generated constructor stub
	}

	public String getManagerName() {
		return ManagerName;
	}

	public void setManagerName(String managerName) {
		ManagerName = managerName;
	}
	
	

}
