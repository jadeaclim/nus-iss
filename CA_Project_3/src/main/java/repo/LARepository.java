package com.team1.iss.trial.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team1.iss.trial.common.CommConstants;
import com.team1.iss.trial.domain.LA;



public interface LARepository extends JpaRepository<LA, Integer>, JpaSpecificationExecutor<LA> {

    @Query("FROM LA WHERE uid = ?1")
    public List<LA> findLADetailsByUid(int uid);

    @Query("FROM LA WHERE owner_id = ?1")
    public List<LA> findLADetailsByOwnerId(int ownerid);

    @Query("FROM LA WHERE status = ?1")
    public List<LA> findLADetailsByStatus(String status);

    // Check overlap -> return a list of overlaping with new la
	@Query(value = "SELECT * FROM la a where (:new_start <= a.to_time) and (:new_end >= a.from_time) and a.from_time>=:currentYear and a.owner_id=:ownerId and (a.status = 'APPROVED' or a.status = 'APPLIED' or a.status = 'UPDATED')" ,nativeQuery=true)
	public List<LA> findLAOverlap(@Param("new_start")long new_start, @Param("new_end")long new_end, @Param("ownerId") int ownerId, @Param("currentYear") long currentYear);//1593224802 current timestamp


	@Query(value = "SELECT a FROM LA a")
	List<LA> findAllLeave();
	
	@Query(value = "SELECT a FROM LA a where a.fromTime>=:currentYear and a.owner.uid=:ownerId")
	List<LA> findAllLeaveByOwnerId(@Param("ownerId") int ownerId,@Param("currentYear") long currentYear);//1593224802 current timestamp
	 
	@Query(value = "SELECT a FROM LA a where a.status="+CommConstants.ApplicationStatus.APPLIED)
	public List<LA> getAppliedLA();
	
	@Query(value = "SELECT a FROM LA a where a.status="+CommConstants.ApplicationStatus.UPDATED)
	public List<LA> getUpdatedLA();

	@Query(value = "SELECT a FROM LA a where a.status="+CommConstants.ApplicationStatus.DELETED)
	public List<LA> getDeletedLA();

	@Query(value = "SELECT a FROM LA a where a.status="+CommConstants.ApplicationStatus.CANCELLED, nativeQuery = true)
	public List<LA> getCancelledLA();

	@Query(value = "SELECT a FROM LA a where a.status="+CommConstants.ApplicationStatus.REJECTED)
	public List<LA> getRejectedLA();

	@Query(value = "SELECT * FROM la  where status="+CommConstants.ApplicationStatus.APPROVED, nativeQuery = true)
	public List<LA> getApprovedLA();

	@Query(value = "SELECT a FROM LA a, User u where a.owner.uid=u.uid AND u.manager.uid=?1 AND a.status in ('APPLIED','UPDATED') order by a.owner.uid")
	public List<LA> getPendingLA(int manageruid);
	
	@Query(value = "SELECT a from LA a where owner_id=?1 order by uid desc")
	public ArrayList<LA> findLAByOwnerId(int uid);
	
	@Query(value = "SELECT * FROM la WHERE from_time>=:currentYear and owner_id=:owner_id",
		    countQuery = "SELECT count(*) FROM la WHERE from_time>=:currentYear and owner_id=:owner_id",
		    nativeQuery = true)
	Page<LA> findAllLeaveByOwnerId(Pageable pageable, @Param("owner_id") int ownerid, @Param("currentYear") Long currentYear);

	@Query(value = "SELECT a FROM LA a where a.fromTime>=:currentYear and a.owner.uid=:ownerId and a.status='APPROVED' and a.type='ANNUAL_LEAVE'")
	List<LA> findAllApprovedAnnualLeaveByOwnerId(@Param("ownerId") int ownerId, @Param("currentYear") Long currentYear);

	@Query(value = "SELECT a FROM LA a where a.fromTime>=:currentYear and a.owner.uid=:ownerId and a.status='APPROVED' and a.type='MEDICAL_LEAVE'")
	List<LA> findAllApprovedMedicalLeaveByOwnerId(@Param("ownerId") int ownerId, @Param("currentYear") Long currentYear);

	@Query(value = "SELECT a FROM LA a where a.fromTime>=:currentYear and a.owner.uid=:ownerId and a.status='APPROVED' and a.type='COMPENSATION_LEAVE'")
	List<LA> findAllApprovedCompensationLeaveByOwnerId(@Param("ownerId") int ownerId, @Param("currentYear") Long currentYear);

	@Query(value = "SELECT a FROM LA a, User u where a.owner.uid=u.uid AND u.manager.uid=?3 AND a.status='APPROVED' AND a.fromTime <= ?2 AND a.toTime >= ?1 order by a.fromTime desc")
	public ArrayList<LA> findEmployeesOnLeaveDuringPeriod(long fromTime, long toTime, int manageruid);
	

}
