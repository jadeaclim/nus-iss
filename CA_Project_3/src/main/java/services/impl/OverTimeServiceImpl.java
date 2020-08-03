package com.team1.iss.trial.services.impl;


import com.team1.iss.trial.domain.OverTime;
import com.team1.iss.trial.repo.OverTimeRepository;
import com.team1.iss.trial.services.interfaces.IOverTimeService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

/*
 * Author: YC
 * */
@Service
public class OverTimeServiceImpl implements IOverTimeService {
	
	@Autowired
    OverTimeRepository otrepo;
	
	@Override
	public ArrayList<OverTime> findAll() {
		ArrayList<OverTime> list = (ArrayList<OverTime>) otrepo.findAll();
		return list;
	}
	   
	@Override
	public OverTime getOtById(int id) {
		return otrepo.findById(id).get();
	}
	
    //Save the OverTime
	@Override
	public void saveOt(OverTime ot) {
		otrepo.save(ot);
	}
	
	//Update the OverTime
	@Override
	public void updateOt(OverTime ot) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public List<OverTime> findOtByOwnerId(int uid) {
		List<OverTime> ots=otrepo.findByOwnerId(uid);
		return ots;
	}

	@Override
	public void calculateHours(OverTime overtime) {
		long startTime = overtime.getStartTime();
		long endTime = overtime.getEndTime();
		int hours = (int) ((endTime - startTime) / (1000 * 60 * 60));
		overtime.setHours(hours);
	}

	@Override
	public List<OverTime> findAllByOwnerId(int uid) {
		List<OverTime> ots=otrepo.findAllByOwnerId(uid);
		return ots;
	}
}
