package com.team1.iss.trial.services.impl;

import com.team1.iss.trial.common.utils.TimeUtil;
import com.team1.iss.trial.domain.PublicHoliday;
import com.team1.iss.trial.repo.PublicHolidayRepository;
import com.team1.iss.trial.services.interfaces.IPublicHolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicHolidayServiceImpl implements IPublicHolidayService {

    @Autowired
    PublicHolidayRepository publicHolidayRepository;

    @Override
    public List<PublicHoliday> getCurrentYearPublicHoliday() {
        List<PublicHoliday> publicHolidays=publicHolidayRepository.findCurrentYearPublicHolidays(TimeUtil.getYearStartTime(TimeUtil.getCurrentTimestamp()));
        return publicHolidays;
    }
}
