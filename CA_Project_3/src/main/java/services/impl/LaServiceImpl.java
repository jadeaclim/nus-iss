package com.team1.iss.trial.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.team1.iss.trial.common.CommConstants;
import com.team1.iss.trial.common.utils.TimeUtil;
import com.team1.iss.trial.domain.LA;
import com.team1.iss.trial.domain.PublicHoliday;
import com.team1.iss.trial.repo.LARepository;
import com.team1.iss.trial.repo.PublicHolidayRepository;
import com.team1.iss.trial.services.interfaces.ILaService;


@Service
public class LaServiceImpl implements ILaService {

    @Autowired
    LARepository larepo;

    @Autowired
    PublicHolidayRepository publicHolidayRepository;

    //Return all the LAs
    @Override
    public ArrayList<LA> findAll() {
        ArrayList<LA> list = (ArrayList<LA>) larepo.findAll();
        return list;
    }

    //Return Single LA
    @Override
    public LA getLaById(int id) {
        return larepo.findById(id).get();
    }

    //Save the LA
    @Override
    public void saveLA(LA la) {
        larepo.save(la);
    }

    //Update the LA
    @Override
    public void updateLA(LA la) {
        LA oldLa = larepo.findById(la.getUid()).get();
        oldLa.setStatus(la.getStatus());
        oldLa.setContact(la.getContact());
        oldLa.setReasons(la.getReasons());
        oldLa.setType(la.getType());
        oldLa.setDissemination(la.getDissemination());
        oldLa.setToTime(la.getToTime());
        oldLa.setFromTime(la.getFromTime());
        larepo.save(oldLa);
    }

    //Remove the LA
    public void deleteLA(int id) {
        LA la = larepo.findById(id).get();
        la.setStatus(CommConstants.ApplicationStatus.CANCELLED);
        larepo.save(la);
    }

    @Override
    public List<LA> findLaByOwnerId(int uid) {
        List<LA> las = larepo.findAllLeaveByOwnerId(uid, TimeUtil.getYearStartTime(TimeUtil.getCurrentTimestamp()));
        for (LA la : las) {
            calculateApplicationDuration(la);
        }

        return las;
    }
    
    @Override
    public Page<LA> findLaByOwnerIdPageable(Pageable pageable, int uid) {
    	Page<LA> las = larepo.findAllLeaveByOwnerId(pageable, uid,TimeUtil.getYearStartTime(TimeUtil.getCurrentTimestamp()) );
        for (LA la : las) {
            calculateApplicationDuration(la);
        }
        return las;
    }


    @Override
    public void calculateApplicationDuration(LA la) {
        long fromTime = la.getFromTime();
        long toTime = la.getToTime();
        float l = (toTime - fromTime) / (long)(1000 * 60 * 60 * 24);
        float duration =  ((toTime - fromTime) / (long) (1000 * 60 * 60 * 12-1000))/2.0f;
        if (la.getType().equals(CommConstants.LeaveType.ANNUAL_LEAVE)) {
            if (duration <= 14) {
                List<PublicHoliday> holidays =
                        publicHolidayRepository.findCurrentYearPublicHolidays(TimeUtil.getYearStartTime(TimeUtil.getCurrentTimestamp()))
                        .stream()
                        .filter(holiday->{
                            return !TimeUtil.getWeek(holiday.getDay()).equals("Saturday")&&!TimeUtil.getWeek(holiday.getDay()).equals("Sunday");
                        })
                        .collect(Collectors.toList());
                for (long i = fromTime; i <= toTime; i += 1000 * 60 * 60 * 24) {
                    if (TimeUtil.getWeek(i).equals("Saturday") || TimeUtil.getWeek(i).equals("Sunday")) {
                        duration--;
                    }
                    for (PublicHoliday holiday : holidays) {
                        if(holiday.getDay()==i){
                            duration--;
                        }
                    }

                }
            }
        }
        la.setDuration(duration);
    }

    public List<LA> findLAOverlap(long new_start, long new_end, int ownerId, long currentYear) {
        List<LA> overlapList = larepo.findLAOverlap(new_start, new_end, ownerId, currentYear);
        return overlapList;
    }
}
