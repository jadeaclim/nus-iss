package com.team1.iss.trial.services.interfaces;

import com.team1.iss.trial.domain.PublicHoliday;

import java.util.List;

public interface IPublicHolidayService {

    List<PublicHoliday> getCurrentYearPublicHoliday();
}
