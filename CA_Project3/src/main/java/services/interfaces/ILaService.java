package com.team1.iss.trial.services.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.team1.iss.trial.domain.LA;
import org.springframework.data.repository.query.Param;

/*
* Author: YC
* */
public interface ILaService {

     ArrayList<LA> findAll();

     LA getLaById(int id);

     void saveLA(LA la);

     void updateLA(LA la);

     void deleteLA(int id);

    List<LA> findLaByOwnerId(int uid);

    void calculateApplicationDuration(LA la);
    
    Page<LA> findLaByOwnerIdPageable(Pageable pageable, int uid);

    //test
    List<LA> findLAOverlap(long new_start, long new_end, int ownerId, long currentYear);

}
