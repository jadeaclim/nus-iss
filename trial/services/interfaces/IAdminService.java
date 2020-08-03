package com.team1.iss.trial.services.interfaces;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team1.iss.trial.domain.Manager;
import com.team1.iss.trial.domain.PublicHoliday;
import com.team1.iss.trial.domain.User;
import com.team1.iss.trial.domain.FormEditUser;
import com.team1.iss.trial.domain.FormPh;



public interface IAdminService {
	
	public boolean updateUserType(String userType, int uid);
	public boolean updateUserManager(int managerId, int uid);
	public String findUserManagerby(int uid);
	public FormEditUser editUser(int uid);
	public boolean updateUser(FormEditUser fu);
	
	public boolean saveNewUser(User user);
	
	public ArrayList<User> findAll();


	public User findUserById(Integer id);

	public ArrayList<Manager> findAllManager();
	public int findlatestUID();

	public boolean updateAllMedicalLeave(int medical_leave_entitlement);
	public boolean updateProfAnnualLeave(int profAL);
	public boolean updateAdminAnnualLeave(int adminAL);
	Page<User> getPaginatedUsers(Pageable pageable);
	public List<PublicHoliday> getAllPH();
	boolean savePh(FormPh phform);
	boolean updatePh(FormPh phform);
	
	public ArrayList<User> getAllUsers(String word);
	public PublicHoliday getPh(int uid);
	public boolean deletePh(int uid);
	
	public boolean isEmailAlreadyInUse (String email);
	public String getCurrentName();
}

	

