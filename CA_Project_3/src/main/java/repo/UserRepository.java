package com.team1.iss.trial.repo;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team1.iss.trial.domain.User;



public interface UserRepository extends JpaRepository<User, Integer>{
	
	@Query(value="select * from user where enabled=1 AND manager_id=:managerid",nativeQuery=true)
	public List<User> getEmployeelistByManagerId(@Param("managerid") int managerid);
	
	@Query(value = "select uid from user where email=:email",nativeQuery = true)
	int findUserUidByEmail(@Param("email") String email);

	@Modifying
	@Transactional
	@Query("update User set user_type=:userType where uid=:uid")
	public void updateUserType(@Param("userType") String userType,@Param("uid") int uid); 
	
	@Modifying
	@Transactional
	@Query("update User set manager_id=:managerId where uid=:uid")
	public void updateUserManager(@Param("managerId") int managerId, @Param("uid") int uid);
	

	@Query(value = "select manager_id from User where uid=:uid", nativeQuery=true)
	public void findUserManagerId(@Param("uid") int employeeId);
	

	@Query(value = "SELECT um.name FROM user ue, user um where ue.manager_id=um.uid and ue.uid=?1", nativeQuery=true)
	public String findUserManagerName(@Param("uid") int employeeId);
	

	@Modifying
	@Transactional
	@Query("update User set name=:name, enabled=:enabled, user_type=:userType, annual_leave_entitlement=:al, medical_leave_entitlement=:ml where uid=:uid")
	public void updateUser(@Param("name")String name, @Param("enabled")boolean enabled, @Param("userType")String userType, @Param("al")int al,@Param("ml")int ml, @Param("uid") int uid); 
	

	@Query(value= "select uid from user where uid = (select max(uid) from user)", nativeQuery=true)
	public int findlatestUID();
	
	@Modifying
	@Transactional
	@Query("update User set medical_leave_entitlement=:medicalleaveentitlement")
	public void updateAllMedicalLeave(@Param("medicalleaveentitlement") int medicalleaveentitlement);
	
	@Modifying
	@Transactional
	@Query("update User set annual_leave_entitlement = :ALprof where user_type != 'ADMIN' ")
	public void updateProfAnnualLeave(@Param("ALprof") int ALprof);
	
	@Modifying
	@Transactional
	@Query("update User set annual_leave_entitlement = :ALadmin where user_type LIKE 'ADMIN' ")
	public void updateAdminAnnualLeave(@Param("ALadmin") int ALadmin);
	
	//Pagination
	Page<User> findAll(Pageable pageable);
	
	
	@Query("select u from User u where u.name like %?1% OR email like %?1% ") 
	public ArrayList<User> findByName(@Param("word") String word);
	
    @Query(value = "select m.email from user u, user m where u.manager_id=m.uid and u.email =:email", nativeQuery=true)
    public String findManageremailbyuseremail(@Param("email") String email);
   
    @Query(value = "select u.email from user u, la la where u.uid=la.owner_id and la.uid=?1 LIMIT 1",nativeQuery = true)
    public String findemailbyLAUID(@Param("uid") int uid);
	
	@Query(value = "select email from user where email= :email LIMIT 1", nativeQuery=true)
    public String findEmail(@Param("email") String email);
	
	
	@Query("select u.name from User u where u.email=:email")
	public String findNameByEmail(@Param("email") String email); 
};








