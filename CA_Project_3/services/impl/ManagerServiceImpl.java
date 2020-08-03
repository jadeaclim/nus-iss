package com.team1.iss.trial.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.team1.iss.trial.common.utils.TimeUtil;
import com.team1.iss.trial.domain.EmployeeToCSV;
import com.team1.iss.trial.domain.LA;
import com.team1.iss.trial.domain.LACsvFile;
import com.team1.iss.trial.domain.LeaveHistoryToCSV;
import com.team1.iss.trial.domain.OverTime;
import com.team1.iss.trial.domain.OverTimeToCSV;
import com.team1.iss.trial.domain.User;
import com.team1.iss.trial.repo.LARepository;
import com.team1.iss.trial.repo.OverTimeRepository;
import com.team1.iss.trial.repo.UserRepository;
import com.team1.iss.trial.services.interfaces.ILaService;
import com.team1.iss.trial.services.interfaces.IManagerService;



@Service
@Primary
public class ManagerServiceImpl extends EmployeeServiceImpl implements IManagerService {

	@Autowired
	LARepository laRepo;


	@Autowired
	OverTimeRepository otRepo;
	
	@Autowired
	UserRepository uRepo;
	
	@Autowired
	ILaService laService;

	@Override
	public ArrayList<LA> findPendingApplications() {
		int managerid=getCurrentUid();
		ArrayList<LA> applicationlistForApproval= (ArrayList<LA>)laRepo.getPendingLA(managerid);
		for (Iterator<LA> iterator = applicationlistForApproval.iterator(); iterator.hasNext();) {
			LA la = iterator.next();
			laService.calculateApplicationDuration(la);
		}
		return applicationlistForApproval;
	}

	@Override
	public ArrayList<LA> findAllLeave() {
		ArrayList<LA> listofallLA=(ArrayList<LA>)laRepo.findAllLeave();
		return listofallLA;
	}

	@Override
	public LA findLAByID(Integer uid) {
		return laRepo.findLADetailsByUid(uid).get(0);
	}

	@Override
	public void saveLA(LA la) {
		laRepo.save(la);
	}
	
	@Override
	public void saveOverTime(OverTime ot) {
		otRepo.save(ot);
	}

	@Override
	public ArrayList<OverTime> findClaims() {
		int managerid = getCurrentUid();
		ArrayList<OverTime> listofallclaims=(ArrayList<OverTime>)otRepo.findAllClaims(managerid);
		return listofallclaims;
	}
	
	@Override
	public int getCurrentUid() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		return uRepo.findUserUidByEmail(email);
	}

	@Override
	public Page<User> getPaginatedEmployees(PageRequest pageable) {
		return uRepo.findAll(pageable);
	}

	@Override
	public ArrayList<User> getAllEmployees(String word) {
		return uRepo.findByName(word);
	}
	
	@Override
	public ArrayList<User> getEmolyeeList(int managerid) {
		ArrayList<User> employeeList=(ArrayList<User>)uRepo.getEmployeelistByManagerId(managerid);
		return employeeList;
	}
	
	@Override
	public ArrayList<LACsvFile> LaCsvMapper (List<LA> la){
		ArrayList<LACsvFile> newlist = new ArrayList<>();	
		for(LA l:la) {
			LACsvFile n = new LACsvFile();
			n.setLAuid(l.getUid());
			n.setContact(l.getContact());	
			n.setFromTime(TimeUtil.convertTimestampToTimeFormat(l.getFromTime()));
			n.setToTime(TimeUtil.convertTimestampToTimeFormat(l.getToTime()));
			n.setReasons(l.getReasons());
			n.setType(l.getType());	
			if(l.getDissemination() != null) {
				n.setStandin(l.getDissemination().getName());
			}
			else {
				n.setStandin("None");
			}
			n.setStatus(l.getStatus());
			n.setOwnername(l.getOwner().getName());
			n.setOwnerID(l.getOwner().getUid());
			newlist.add(n);
		}	
		return newlist;
	}
	@Override
    public List<OverTimeToCSV> convertOverTimetoCSV(ArrayList<OverTime> compensationlist) {
        List<OverTimeToCSV> list = new ArrayList<>();
        for (OverTime compensation : compensationlist) {
            
              OverTimeToCSV overtimetocsv=new OverTimeToCSV();
              overtimetocsv.setUid(String.valueOf(compensation.getUid()));
              overtimetocsv.setOwner(compensation.getOwner().getName());
              overtimetocsv.setStartTime(TimeUtil.convertTimestampToTimeFormat(compensation.getStartTime()));
              overtimetocsv.setEndTime(TimeUtil.convertTimestampToTimeFormat(compensation.getEndTime()));
              overtimetocsv.setHours(String.valueOf((compensation.getEndTime()-compensation.getStartTime())/3600000));
              overtimetocsv.setStatus(compensation.getStatus());
              
              list.add(overtimetocsv); 
              
              }
        return list;
    }
	
	@Override
	public long getFromTime(Integer uid) {
		return laRepo.findLADetailsByUid(uid).get(0).getFromTime();
	}

	@Override
	public long getToTIme(Integer uid) {
		return laRepo.findLADetailsByUid(uid).get(0).getToTime();
	}

	@Override
	public ArrayList<LA> findEmployeesOnLeave(long fromTime, long toTime) {
		int managerid=getCurrentUid();
		ArrayList<LA> employeeList=laRepo.findEmployeesOnLeaveDuringPeriod(fromTime, toTime, managerid);
		return employeeList;
	}


	@Override
	public List<EmployeeToCSV> convertEmployeeListtoCSV(ArrayList<User> employeeList) {
		List<EmployeeToCSV> list = new ArrayList<>();
        for (User employee : employeeList) {
            
        	EmployeeToCSV employeetocsv=new EmployeeToCSV();
              employeetocsv.setID(String.valueOf(employee.getUid()));
              employeetocsv.setName(employee.getName());
              employeetocsv.setEmail(employee.getEmail());
              employeetocsv.setType(employee.getUserType());
              list.add(employeetocsv); 
        }
        return list;
		
	}

	@Override
	public List<LeaveHistoryToCSV> convertLeaveHistorytoCSV(List<LA> leavehistory) {
		List<LeaveHistoryToCSV> list = new ArrayList<>();
        for (LA leave : leavehistory) {
            
        	LeaveHistoryToCSV leavehistorytocsv=new LeaveHistoryToCSV();
              leavehistorytocsv.setLeaveID(String.valueOf(leave.getUid()));
              leavehistorytocsv.setType(leave.getType());
              leavehistorytocsv.setFrom(TimeUtil.convertTimestampToTimeFormat(leave.getFromTime()));
              leavehistorytocsv.setTo(TimeUtil.convertTimestampToTimeFormat(leave.getToTime()));
              leavehistorytocsv.setReason(leave.getReasons());
              leavehistorytocsv.setStatus(leave.getStatus());
              leavehistorytocsv.setNoOfDays(String.valueOf(leave.getDuration()));
              list.add(leavehistorytocsv); 
        }
        return list;
	}
	
	@Override
	public String getCurrentName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		return uRepo.findNameByEmail(email);
	}
}
