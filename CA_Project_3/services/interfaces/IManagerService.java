package com.team1.iss.trial.services.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.team1.iss.trial.domain.EmployeeToCSV;
import com.team1.iss.trial.domain.LA;
import com.team1.iss.trial.domain.LACsvFile;
import com.team1.iss.trial.domain.LeaveHistoryToCSV;
import com.team1.iss.trial.domain.OverTime;
import com.team1.iss.trial.domain.OverTimeToCSV;
import com.team1.iss.trial.domain.User;


public interface IManagerService {

	public ArrayList<LA> findPendingApplications();
	public ArrayList<LA> findAllLeave();
	public LA findLAByID(Integer uid);
	public void saveLA(LA la);
	public void saveOverTime(OverTime ot);
	public ArrayList<OverTime> findClaims();
	public int getCurrentUid();
	public ArrayList<User> getEmolyeeList(int managerid);
//	public ArrayList<LA> findEmployeeLeaveByEmployeeId(int uid);
	public Page<User> getPaginatedEmployees(PageRequest pageable);
	public ArrayList<User> getAllEmployees(String word);
	public ArrayList<LACsvFile> LaCsvMapper(List<LA> la);
	public List<OverTimeToCSV> convertOverTimetoCSV(ArrayList<OverTime> compensationlist);
	public long getFromTime(Integer uid);
	public long getToTIme(Integer uid);
	public ArrayList<LA> findEmployeesOnLeave(long fromTime, long toTime);
	public List<EmployeeToCSV> convertEmployeeListtoCSV(ArrayList<User> employeeList);
	public List<LeaveHistoryToCSV > convertLeaveHistorytoCSV(List<LA> leavehistory);
	public String getCurrentName();

}
