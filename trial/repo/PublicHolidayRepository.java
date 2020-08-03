package com.team1.iss.trial.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team1.iss.trial.domain.PublicHoliday;

import java.util.List;

public interface PublicHolidayRepository  extends JpaRepository<PublicHoliday, Integer>{

	
	@Modifying
	@Transactional
	@Query("update PublicHoliday set name=:name, day=:day where uid=:uid")
	public void updatePH(@Param("uid")int uid, @Param("day")long millisecondsSinceEpoch, @Param("name")String name);


	@Query("select p from PublicHoliday  p where p.day>=:currentYear")
	List<PublicHoliday> findCurrentYearPublicHolidays(@Param("currentYear") Long currentYear);
}
