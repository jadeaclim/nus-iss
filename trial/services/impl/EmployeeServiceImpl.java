package com.team1.iss.trial.services.impl;

import java.util.List;

import com.team1.iss.trial.common.utils.TimeUtil;
import com.team1.iss.trial.domain.OverTime;
import com.team1.iss.trial.domain.User;
import com.team1.iss.trial.repo.OverTimeRepository;
import com.team1.iss.trial.repo.UserRepository;
import com.team1.iss.trial.services.interfaces.ILaService;
import com.team1.iss.trial.services.interfaces.IOverTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.team1.iss.trial.domain.LA;
import com.team1.iss.trial.repo.EmployeeRepository;
import com.team1.iss.trial.repo.LARepository;
import com.team1.iss.trial.services.interfaces.IEmployeeService;

@Service
public class EmployeeServiceImpl implements IEmployeeService {
	
	 @Autowired
	 LARepository laRepo;

	 @Autowired
	 UserRepository userRepository;

	 @Autowired
	ILaService laService;

	 @Autowired
	OverTimeRepository overTimeRepository;

	 @Autowired
	 IOverTimeService overTimeService;
	 
	 
	 @Override
	 public List<LA> findAllLeave() {
			return (List<LA>) laRepo.findAllLeave();
	 }
	
	 public List<LA> getAppliedLA() {
			return (List<LA>) laRepo.getAppliedLA();
	 }
	 
	 public List<LA> getUpdatedLA() {
			return (List<LA>) laRepo.getUpdatedLA();
	 }
	 
	 public List<LA> getDeletedLA() {
			return (List<LA>) laRepo.getDeletedLA();
	 }
	 
	 public List<LA> getCancelledLA() {
			return (List<LA>) laRepo.getCancelledLA();
	 }
	 
	 public List<LA> getRejectedLA() {
			return (List<LA>) laRepo.getRejectedLA();
	 }
	 
	 public List<LA> getApprovedLA() {
			return (List<LA>) laRepo.getApprovedLA();
	 }
	 
	 @Autowired
	 EmployeeRepository eRepo;

	 @Override
	 public List<User> findAllUsers() {
		 List<User> users = userRepository.findAll();
		 return users;
	 }

	@Override
	public float getAnnualApplicationBalance(LA la) {
		User user = userRepository.findById(la.getOwner().getUid()).get();
		List<LA> las = laRepo.findAllApprovedAnnualLeaveByOwnerId(la.getOwner().getUid(), TimeUtil.getYearStartTime(TimeUtil.getCurrentTimestamp()));
		float usedDay=0;
		for (LA item : las) {
			laService.calculateApplicationDuration(item);
			usedDay+=item.getDuration();
		}
		return user.getAnnualLeaveEntitlement()-usedDay;

	}
	
	@Override
	public float getAnnualApplicationBalance(int ownerId) {
		User user = userRepository.findById(ownerId).get();
		List<LA> las = laRepo.findAllApprovedAnnualLeaveByOwnerId(ownerId, TimeUtil.getYearStartTime(TimeUtil.getCurrentTimestamp()));
		float usedDay=0;
		for (LA item : las) {
			laService.calculateApplicationDuration(item);
			usedDay+=item.getDuration();
		}
		return user.getAnnualLeaveEntitlement()-usedDay;
	}
	
	

	@Override
	public float getMedicalApplicationBalance(LA la) {
		User user = userRepository.findById(la.getOwner().getUid()).get();
		List<LA> las = laRepo.findAllApprovedMedicalLeaveByOwnerId(la.getOwner().getUid(), TimeUtil.getYearStartTime(TimeUtil.getCurrentTimestamp()));
		float usedDay=0;
		for (LA item : las) {
			laService.calculateApplicationDuration(item);
			usedDay+=item.getDuration();
		}
		return user.getMedicalLeaveEntitlement()-usedDay;
	}
	
	@Override
	public float getMedicalApplicationBalance(int ownerId) {
		User user = userRepository.findById(ownerId).get();
		List<LA> las = laRepo.findAllApprovedMedicalLeaveByOwnerId(ownerId, TimeUtil.getYearStartTime(TimeUtil.getCurrentTimestamp()));
		float usedDay=0;
		for (LA item : las) {
			laService.calculateApplicationDuration(item);
			usedDay+=item.getDuration();
		}
		return user.getMedicalLeaveEntitlement()-usedDay;
	}

	@Override
	public float getCompensationApplicationBalance(LA la) {
		List<OverTime> overtimes = overTimeRepository.findCurrentYearApprovedOverTimeByOwnerId(la.getOwner().getUid(), TimeUtil.getYearStartTime(TimeUtil.getCurrentTimestamp()));
		int totalOverTime=0;
		for (OverTime overtime : overtimes) {
			overTimeService.calculateHours(overtime);
			totalOverTime+=overtime.getHours();
		}
		List<LA> las=laRepo.findAllApprovedCompensationLeaveByOwnerId(la.getOwner().getUid(), TimeUtil.getYearStartTime(TimeUtil.getCurrentTimestamp()));
		float usedDay=0;
		for (LA item : las) {
			laService.calculateApplicationDuration(item);
			usedDay+=item.getDuration();
		}
		return totalOverTime/8.0f-usedDay;
	}

	@Override
	public float getCompensationApplicationBalance(int ownerId) {
		List<OverTime> overtimes = overTimeRepository.findCurrentYearApprovedOverTimeByOwnerId(ownerId, TimeUtil.getYearStartTime(TimeUtil.getCurrentTimestamp()));
		int totalOverTime=0;
		for (OverTime overtime : overtimes) {
			overTimeService.calculateHours(overtime);
			totalOverTime+=overtime.getHours();
		}
		List<LA> las=laRepo.findAllApprovedCompensationLeaveByOwnerId(ownerId, TimeUtil.getYearStartTime(TimeUtil.getCurrentTimestamp()));
		float usedDay=0;
		for (LA item : las) {
			laService.calculateApplicationDuration(item);
			usedDay+=item.getDuration();
		}
		return totalOverTime/8.0f-usedDay;
	}

	@Override
	public User getUserByUid(int uid) {
		// TODO Auto-generated method stub
		User user = userRepository.findById(uid).get();
		return user;
	}	
}
