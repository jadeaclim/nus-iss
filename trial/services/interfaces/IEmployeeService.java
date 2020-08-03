package com.team1.iss.trial.services.interfaces;


import java.util.List;

import com.team1.iss.trial.domain.Employee;
import com.team1.iss.trial.domain.LA;
import com.team1.iss.trial.domain.User;


public interface IEmployeeService {

	List<LA> findAllLeave();

	List<User> findAllUsers();

	float getAnnualApplicationBalance(LA la);
	
	float getAnnualApplicationBalance(int ownerId);

	float getMedicalApplicationBalance(LA la);
	
	float getMedicalApplicationBalance(int ownerId);

	float getCompensationApplicationBalance(LA la);
	
	float getCompensationApplicationBalance(int ownerId);

	User getUserByUid(int uid);
}
