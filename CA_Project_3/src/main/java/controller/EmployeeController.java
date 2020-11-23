package com.team1.iss.trial.controller;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.team1.iss.trial.common.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team1.iss.trial.common.CommConstants;
import com.team1.iss.trial.component.RequestLA;
import com.team1.iss.trial.domain.Employee;
import com.team1.iss.trial.domain.LA;
import com.team1.iss.trial.domain.OverTime;
import com.team1.iss.trial.domain.PublicHoliday;
import com.team1.iss.trial.domain.User;
import com.team1.iss.trial.repo.UserRepository;

import com.team1.iss.trial.services.interfaces.IAdminService;

import com.team1.iss.trial.services.impl.EmailServiceImpl;
import com.team1.iss.trial.services.interfaces.IEmailService;

import com.team1.iss.trial.services.interfaces.IEmployeeService;
import com.team1.iss.trial.services.interfaces.ILaService;
import com.team1.iss.trial.services.interfaces.IOverTimeService;
import com.team1.iss.trial.services.interfaces.IPublicHolidayService;

@Controller
public class EmployeeController {
	@Autowired
	private ILaService laService;

	@Autowired
	private IEmployeeService eService;
	
	@Autowired
	private IAdminService aService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private IOverTimeService overTimeService;

	@Autowired
	private IPublicHolidayService publicHolidayService;
	

	@RequestMapping("/employee")
	public String user(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		int uid=userRepository.findUserUidByEmail(email);
		User user=eService.getUserByUid(uid);
		int sumOTHours=0;
		for (Iterator<OverTime> iterator = overTimeService.findOtByOwnerId(uid).iterator(); iterator.hasNext();) {
			OverTime overTime = iterator.next();
			overTimeService.calculateHours(overTime);
			sumOTHours+= overTime.getHours();
				}
		model.addAttribute("ALEntitled", user.getAnnualLeaveEntitlement());
		model.addAttribute("ALBalance", eService.getAnnualApplicationBalance(uid));
		model.addAttribute("MLEntitled", user.getMedicalLeaveEntitlement());
		model.addAttribute("MLBalance", eService.getMedicalApplicationBalance(uid));
		model.addAttribute("CLfromOT",sumOTHours );
		model.addAttribute("CLBalance", eService.getCompensationApplicationBalance(uid));
		return ("employee/eHome");
	}
	
	@Autowired
	private IEmailService emservice;
	
	@Autowired
	public void setEmailService(EmailServiceImpl emserviceimpl) {
		this.emservice = emserviceimpl;
	}


	/**
	 * go to the apply application page
	 * @param model
	 * @return
	 */
	@GetMapping("/employee/apply")
	public String addLeave(Model model) {
		List<String> applicationType = new ArrayList();
		applicationType.add(CommConstants.LeaveType.ANNUAL_LEAVE);
		applicationType.add(CommConstants.LeaveType.MEDICAL_LEAVE);
		applicationType.add(CommConstants.LeaveType.COMPENSATION_LEAVE);
		model.addAttribute("types", applicationType);
		List<User> users = eService.findAllUsers();
		List<User> collect = users.stream().filter(user -> !user.getUserType().equals(CommConstants.UserType.AMDIN))
				.collect(Collectors.toList());
		model.addAttribute("employees",collect);
		return "employee/leave-form";
	}

	/**
	 * submit leave application
	 * @param la
	 * @param model
	 * @return
	 */
	@PostMapping("/employee/la")
    public String saveLA(@RequestLA LA la, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		int uid=userRepository.findUserUidByEmail(email);
		la.setOwner(new Employee(uid));
		la.setStatus(CommConstants.ApplicationStatus.APPLIED);
		boolean isLAValidate=true;
		if(la.getToTime()-la.getFromTime()<=0){
			model.addAttribute("msg","End date cannot be before start date");
			isLAValidate=false;
		}
		//check if fromtime is a public holiday
		List<PublicHoliday> holidays = publicHolidayService.getCurrentYearPublicHoliday()
				.stream()
				.filter(holiday -> {
					return holiday.getDay() == la.getFromTime();
				})
				.collect(Collectors.toList());
		if(holidays.size()>0){
			model.addAttribute("msg","Start date cannot be a public holiday");
			isLAValidate=false;
		}
		//balance check
		//calculate annual application leave duration
		laService.calculateApplicationDuration(la);
		if(la.getType().equals(CommConstants.LeaveType.ANNUAL_LEAVE)){
			float balance = eService.getAnnualApplicationBalance(la);
			if(balance<la.getDuration()){
				model.addAttribute("msg","Your annual leave balance is insufficient, only "+balance+" days left");
				isLAValidate=false;
			}
		}else if(la.getType().equals(CommConstants.LeaveType.MEDICAL_LEAVE)){
			float balance=eService.getMedicalApplicationBalance(la);
			if(balance<la.getDuration()){
				model.addAttribute("msg","Your medical leave balance is insufficient, only "+balance+" days left");
				isLAValidate=false;
			}
		}else{
			float balance=eService.getCompensationApplicationBalance(la);
			if(balance<la.getDuration()){
				model.addAttribute("msg","Your compensation leave balance is insufficient, only "+balance+" days left");
				isLAValidate=false;
			}
		}
		// check if la is overlaped with existing leave
		List<LA> existing_LA = laService.findLAOverlap(la.getFromTime(), la.getToTime(), la.getOwner().getUid(), TimeUtil.getYearStartTime(TimeUtil.getCurrentTimestamp())); //1593224802
		if (existing_LA.size() > 0) {
			model.addAttribute("msg","The dates of this application overlap with an existing application");
			List<String> applicationType = new ArrayList();
			applicationType.add(CommConstants.LeaveType.ANNUAL_LEAVE);
			applicationType.add(CommConstants.LeaveType.MEDICAL_LEAVE);
			applicationType.add(CommConstants.LeaveType.COMPENSATION_LEAVE);
			model.addAttribute("types", applicationType);
			List<User> users = eService.findAllUsers();
			List<User> collect = users.stream().filter(user -> !user.getUserType().equals(CommConstants.UserType.AMDIN))
					.collect(Collectors.toList());
			model.addAttribute("employees",collect);
			return "employee/leave-form";
		}
		if(!isLAValidate){
			List<String> applicationType = new ArrayList();
			applicationType.add(CommConstants.LeaveType.ANNUAL_LEAVE);
			applicationType.add(CommConstants.LeaveType.MEDICAL_LEAVE);
			applicationType.add(CommConstants.LeaveType.COMPENSATION_LEAVE);
			model.addAttribute("types", applicationType);
			List<User> users = eService.findAllUsers();
			List<User> collect = users.stream().filter(user -> !user.getUserType().equals(CommConstants.UserType.AMDIN))
					.collect(Collectors.toList());
			model.addAttribute("employees",collect);
			return "employee/leave-form";
		}
		laService.saveLA(la);
		emservice.sendApplicationEmail();
		return "redirect:/employee/las";
    }

	/**
	 * update leave application
	 * @param la
	 * @param model
	 * @return
	 */
	@PostMapping("/employee/la/{uid}")
	public String updateLA(@RequestLA LA la,Model model) {
		System.out.println(la);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		int uid=userRepository.findUserUidByEmail(email);
		la.setOwner(new Employee(uid));
		la.setStatus(CommConstants.ApplicationStatus.UPDATED);
		boolean isLAValidate=true;
		if(la.getToTime()-la.getFromTime()<=0){
			model.addAttribute("msg","End date cannot be before start date");
			isLAValidate=false;
		}
		//check if fromtime is a public holiday
		List<PublicHoliday> holidays = publicHolidayService.getCurrentYearPublicHoliday()
				.stream()
				.filter(holiday -> {
					return holiday.getDay() == la.getFromTime();
				})
				.collect(Collectors.toList());
		if(holidays.size()>0){
			model.addAttribute("msg","Start date cannot be a public holiday");
			isLAValidate=false;
		}
		//balance check
		//calculate annual application leave duration
		laService.calculateApplicationDuration(la);
		if(la.getType().equals(CommConstants.LeaveType.ANNUAL_LEAVE)){
			float balance = eService.getAnnualApplicationBalance(la);
			if(balance<la.getDuration()){
				model.addAttribute("msg","Your annual leave balance is insufficient, only "+balance+" days left");
				isLAValidate=false;
			}
		}else if(la.getType().equals(CommConstants.LeaveType.MEDICAL_LEAVE)){
			float balance=eService.getMedicalApplicationBalance(la);
			if(balance<la.getDuration()){
				model.addAttribute("msg","Your medical leave balance is insufficient, only "+balance+" days left");
				isLAValidate=false;
			}
		}else {
			float balance=eService.getCompensationApplicationBalance(la);
			if(balance<la.getDuration()){
				model.addAttribute("msg","Your compensation leave balance is insufficient, only "+balance+" days left");
				isLAValidate=false;
			}
		}
		// check if la overlaps with existing leave
		List<LA> existing_LA = laService.findLAOverlap(la.getFromTime(), la.getToTime(), la.getOwner().getUid(), TimeUtil.getYearStartTime(TimeUtil.getCurrentTimestamp()))
				.stream()
				.filter(x->la.getUid()!=x.getUid())
				.collect(Collectors.toList());
		if (existing_LA.size() > 0) {
			model.addAttribute("msg","The dates of this application overlap with an existing application");
			isLAValidate=false;
		}
		if(isLAValidate){
			model.addAttribute("msg","update successfully");
			laService.updateLA(la);
		}
		List<String> applicationType = new ArrayList();
		applicationType.add(CommConstants.LeaveType.ANNUAL_LEAVE);
		applicationType.add(CommConstants.LeaveType.MEDICAL_LEAVE);
		applicationType.add(CommConstants.LeaveType.COMPENSATION_LEAVE);
		model.addAttribute("types", applicationType);
		List<User> users = eService.findAllUsers();
		List<User> collect = users.stream().filter(user -> !user.getUserType().equals(CommConstants.UserType.AMDIN))
				.collect(Collectors.toList());
		model.addAttribute("employees",collect);
		LA updatedLA = laService.getLaById(la.getUid());
		model.addAttribute("la",updatedLA);
		return "employee/laDetails";
	}

	/**
	 * view the leave application detail
	 * @param model
	 * @param uid
	 * @return
	 */
	@GetMapping("/employee/la/{uid}")
	public String getLAById(Model model, @PathVariable int uid) {
		try {
			LA la = laService.getLaById(uid);
			System.out.println(la);
			model.addAttribute("la",la);
			List<String> applicationType = new ArrayList();
			applicationType.add(CommConstants.LeaveType.ANNUAL_LEAVE);
			applicationType.add(CommConstants.LeaveType.MEDICAL_LEAVE);
			applicationType.add(CommConstants.LeaveType.COMPENSATION_LEAVE);
			model.addAttribute("types", applicationType);
			List<User> users = eService.findAllUsers();
			model.addAttribute("employees",users);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "employee/laDetails";
	}

	/**
	 * cancel leave application
	 * @param uid
	 */
	@PostMapping("/employee/cancel/{uid}")
	public String deleteLA(@PathVariable int uid) {
		laService.deleteLA(uid);
		return  "redirect:/employee/las";
	}


	// View employee view history with pagination
		@RequestMapping("/employee/las")
		public String list() {
			return "forward:/employee/las/1";
		}	
		
		@GetMapping("/employee/las/{page}")
		public String la(@PathVariable("page") int page, Model model) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String email = auth.getName();
			int uid=userRepository.findUserUidByEmail(email);
			PageRequest pageable = PageRequest.of(page-1,3);
			Page<LA> lapages = laService.findLaByOwnerIdPageable(pageable, uid);
			model.addAttribute("laList", lapages.getContent());
			
			int totalPages = lapages.getTotalPages();
			if(totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
			}
			return ("employee/lalist");
		}


	/**
	 * show the claim compensation page
	 * @return
	 */
	@GetMapping("/employee/ot")
	public String otPage(){
		return "employee/ot";
	}

	/**
	 * claim an overtime
	 * @param ot
	 * @return
	 */
	@PostMapping("/employee/ot")
	public String submitOT(OverTime ot){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		int uid=userRepository.findUserUidByEmail(email);
		ot.setOwner(new Employee(uid));
		ot.setStatus(CommConstants.ClaimStatus.APPLIED);
		overTimeService.saveOt(ot);
		return "redirect:/employee/ots";
	}

	/**
	 * show the overtime list page
	 * @param model
	 * @return
	 */
	@GetMapping("/employee/ots")
	public String otsPage(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		int uid=userRepository.findUserUidByEmail(email);
		List<OverTime> ots=overTimeService.findAllByOwnerId(uid);
		model.addAttribute("ots",ots);
		return "employee/otList";
	}

}
