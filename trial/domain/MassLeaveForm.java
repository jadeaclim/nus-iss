package com.team1.iss.trial.domain;

import java.util.List;

public class MassLeaveForm{
	
	private int medicalLeaveEntitlement;
	
	private int annualLeaveEntitlementAdmin;
	
	private int annualLeaveEntitlementProf;

	public int getMedicalLeaveEntitlement() {
		return medicalLeaveEntitlement;
	}

	public void setMedicalLeaveEntitlement(int medicalLeaveEntitlement) {
		this.medicalLeaveEntitlement = medicalLeaveEntitlement;
	}

	public int getAnnualLeaveEntitlementAdmin() {
		return annualLeaveEntitlementAdmin;
	}

	public void setAnnualLeaveEntitlementAdmin(int annualLeaveEntitlementAdmin) {
		this.annualLeaveEntitlementAdmin = annualLeaveEntitlementAdmin;
	}

	public int getAnnualLeaveEntitlementProf() {
		return annualLeaveEntitlementProf;
	}

	public void setAnnualLeaveEntitlementProf(int annualLeaveEntitlementProf) {
		this.annualLeaveEntitlementProf = annualLeaveEntitlementProf;
	}

	public MassLeaveForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MassLeaveForm(int medicalLeaveEntitlement, int annualLeaveEntitlementAdmin, int annualLeaveEntitlementProf) {
		super();
		this.medicalLeaveEntitlement = medicalLeaveEntitlement;
		this.annualLeaveEntitlementAdmin = annualLeaveEntitlementAdmin;
		this.annualLeaveEntitlementProf = annualLeaveEntitlementProf;
	}



	

}
