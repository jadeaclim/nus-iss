package com.team1.iss.trial.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.team1.iss.trial.common.CommConstants;
import com.team1.iss.trial.domain.Employee;




public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
	
	//public List<Employee> findAllEmployeesByName(String name);
	
	@Query("select u from User u where u.userType="+CommConstants.UserType.EMPLOYEE)
	public List<Employee> findAllEmployees();

	@Query("select u from User u where u.userType='EMPLOYEE' AND manager_id=?1")
	public List<Employee> findAllSubordinates(int uid);
}
