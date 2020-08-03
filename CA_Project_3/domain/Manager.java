package com.team1.iss.trial.domain;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.team1.iss.trial.common.CommConstants;

@Entity
@DiscriminatorValue(CommConstants.UserType.MANAGER)
public class Manager extends Employee{
	
	@OneToMany(mappedBy = "manager")
	private List<Employee> employees;

	public Manager() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public Manager(int uid, String userType, String name, byte[] photo, String email, int annualLeaveEntitlement,
			int medicalLeaveEntitlement, boolean enabled, List<OverTime> overtimeList, Manager manager,List<Employee> employees) {
		super(uid, userType, name, photo, email, annualLeaveEntitlement, medicalLeaveEntitlement, enabled, overtimeList,
				manager);
		this.employees=employees;
	}



	public Manager(int uid, String userType, String name, byte[] photo, String password, String email,
			int annualLeaveEntitlement, int medicalLeaveEntitlement, boolean enabled, List<OverTime> overtimeList,
			Manager manager,List<Employee> employees) {
		super(uid, userType, name, photo, password, email, annualLeaveEntitlement, medicalLeaveEntitlement, enabled,
				overtimeList, manager);
		this.employees=employees;
	}



	public Manager(int uid, String userType, String name, String password, String email, int annualLeaveEntitlement,
			int medicalLeaveEntitlement, boolean enabled,List<Employee> employees) {
		super(uid, userType, name, password, email, annualLeaveEntitlement, medicalLeaveEntitlement, enabled);
		this.employees=employees;
	}


	public Manager(int uid) {
		super(uid);
		// TODO Auto-generated constructor stub
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	
	

}
