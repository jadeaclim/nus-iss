package com.team1.iss.trial.services.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.List;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.team1.iss.trial.common.CommConstants;
import com.team1.iss.trial.domain.Admin;
import com.team1.iss.trial.domain.Employee;

import com.team1.iss.trial.domain.Manager;
import com.team1.iss.trial.domain.PublicHoliday;
import com.team1.iss.trial.domain.User;
import com.team1.iss.trial.domain.FormEditUser;
import com.team1.iss.trial.domain.FormPh;
import com.team1.iss.trial.repo.ManagerRepository;
import com.team1.iss.trial.repo.PublicHolidayRepository;
import com.team1.iss.trial.repo.UserRepository;
import com.team1.iss.trial.services.interfaces.IAdminService;




@Service
public class AdminServiceImpl implements IAdminService {

	@Autowired
	UserRepository uRepo;

	@Autowired
	ManagerRepository mRepo;
	
	@Autowired
	PublicHolidayRepository pRepo;


	@Override
	public boolean updateUserType(String userType, int uid) {
		uRepo.updateUserType(userType, uid);
		return true;
	}

	@Override
	public boolean updateUserManager(int managerId, int uid) {
		uRepo.updateUserManager(managerId, uid); 
		return true;
	}

	@Override
	public ArrayList<User> findAll() {
		ArrayList<User> list = (ArrayList<User>) uRepo.findAll();
		return list;
	}
	
	@Override
	public Page<User> getPaginatedUsers(Pageable pageable){
		return uRepo.findAll(pageable);		
	}

	@Override
	public FormEditUser editUser(int uid) {
		User u1 = uRepo.findById(uid).get();
		FormEditUser uf = new FormEditUser();		
		uf.setUid(u1.getUid());
		uf.setEmail(u1.getEmail());
		uf.setName(u1.getName());
		uf.setEnabled(u1.isEnabled());				
		uf.setUserType(u1.getUserType());
		uf.setAnnualLeaveEntitlement(u1.getAnnualLeaveEntitlement());
		uf.setMedicalLeaveEntitlement(u1.getMedicalLeaveEntitlement());
		return uf;
	}

	@Override
	public boolean saveNewUser(User user) {
		if(user.getUserType().equals(CommConstants.UserType.AMDIN))
		{
			User a1 = new Admin();
			a1.setAnnualLeaveEntitlement(user.getAnnualLeaveEntitlement());
			a1.setEmail(user.getEmail());
			a1.setMedicalLeaveEntitlement(user.getMedicalLeaveEntitlement());
			a1.setName(user.getName());
			a1.setPassword(user.getPassword());
			a1.setEnabled(user.isEnabled());
			uRepo.save(a1);

		} else if (user.getUserType().equals(CommConstants.UserType.EMPLOYEE))
		{
			User a1 = new Employee();
			a1.setAnnualLeaveEntitlement(user.getAnnualLeaveEntitlement());
			a1.setEmail(user.getEmail());
			a1.setMedicalLeaveEntitlement(user.getMedicalLeaveEntitlement());
			a1.setName(user.getName());
			a1.setPassword(user.getPassword());
			a1.setEnabled(user.isEnabled());
			uRepo.save(a1);
		} else {
			User a1 = new Manager();
			a1.setAnnualLeaveEntitlement(user.getAnnualLeaveEntitlement());
			a1.setEmail(user.getEmail());
			a1.setMedicalLeaveEntitlement(user.getMedicalLeaveEntitlement());
			a1.setName(user.getName());
			a1.setPassword(user.getPassword());
			a1.setEnabled(user.isEnabled());
			uRepo.save(a1);
		}
		return false;
	}

	@Override
	public boolean updateUser(FormEditUser fu) {
		uRepo.updateUser(fu.getName(), fu.isEnabled(), fu.getUserType(), fu.getAnnualLeaveEntitlement(), fu.getMedicalLeaveEntitlement(), fu.getUid());
		return false;
	}

	@Override
	public ArrayList<Manager> findAllManager() {
		ArrayList<Manager> list = (ArrayList<Manager>) mRepo.findAll();
		return list;
	}

	@Override
	public User findUserById(Integer id) {
		return uRepo.findById(id).get();
	}

	@Override
	public String findUserManagerby(int uid) {
		return uRepo.findUserManagerName(uid);
	}

	@Override
	public int findlatestUID() {
		return uRepo.findlatestUID();
	}

	@Override
	public boolean updateAllMedicalLeave(int medical_leave_entitlement) {
		uRepo.updateAllMedicalLeave(medical_leave_entitlement);
		return true;
	}

	@Override
	public boolean updateProfAnnualLeave(int profAL) {
		uRepo.updateProfAnnualLeave(profAL);
		return true;
	}
	
	@Override
	public boolean updateAdminAnnualLeave(int adminAL) {
		uRepo.updateAdminAnnualLeave(adminAL);
		return true;
	}

	@Override
	public ArrayList<User> getAllUsers(String word) {
		return uRepo.findByName(word);
	}

	@Override
	public PublicHoliday getPh(int uid) {
		return pRepo.findById(uid).get();
	}
	
	@Override
	public List<PublicHoliday> getAllPH() {
		return pRepo.findAll();
	}
	
	@Override
	public boolean savePh(FormPh phform) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String stringDate = phform.getDay();
		long millisecondsSinceEpoch = LocalDate.parse(stringDate, dateFormatter).atStartOfDay(ZoneId.of("Asia/Singapore")).toInstant().toEpochMilli();
		PublicHoliday ph = new PublicHoliday();
		ph.setDay(millisecondsSinceEpoch);
		ph.setName(phform.getName());
		pRepo.save(ph);
		return true;		
	}
	
	@Override
	public boolean updatePh(FormPh phform) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String stringDate = phform.getDay();
		long millisecondsSinceEpoch = LocalDate.parse(stringDate, dateFormatter).atStartOfDay(ZoneId.of("Asia/Singapore")).toInstant().toEpochMilli();
		pRepo.updatePH(phform.getUid(),millisecondsSinceEpoch,phform.getName());
		return false;
	}
	
	@Override
	public boolean deletePh(int uid) {
		pRepo.deleteById(uid);
		return true;
	}
	
	@Override
	public boolean isEmailAlreadyInUse (String email) {
		boolean inUse= false;
		if (uRepo.findEmail(email)!= null) {
			inUse = true;
		}
		return inUse;
	}
	
	@Override
	public String getCurrentName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		return uRepo.findNameByEmail(email);
	}
}

