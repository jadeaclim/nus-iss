package com.team1.iss.trial.controller;

import java.util.ArrayList;
import java.util.List;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.team1.iss.trial.common.CommConstants;
import com.team1.iss.trial.common.utils.TimeUtil;
import com.team1.iss.trial.domain.EmployeeToCSV;
import com.team1.iss.trial.domain.LA;
import com.team1.iss.trial.domain.LACsvFile;
import com.team1.iss.trial.domain.LeaveHistoryToCSV;
import com.team1.iss.trial.domain.OverTime;
import com.team1.iss.trial.domain.OverTimeToCSV;
import com.team1.iss.trial.domain.User;
import com.team1.iss.trial.services.impl.EmailServiceImpl;
import com.team1.iss.trial.services.impl.LaServiceImpl;
import com.team1.iss.trial.services.impl.ManagerServiceImpl;
import com.team1.iss.trial.services.impl.OverTimeServiceImpl;
import com.team1.iss.trial.services.interfaces.IEmailService;
import com.team1.iss.trial.services.interfaces.ILaService;
import com.team1.iss.trial.services.interfaces.IManagerService;
import com.team1.iss.trial.services.interfaces.IOverTimeService;

@Controller
@RequestMapping("/manager")
public class ManagerController {
	
	@Autowired
	private IManagerService mservice;
	
	@Autowired
	public void setMservice(ManagerServiceImpl mServiceImpl) {
		this.mservice = mServiceImpl;
	}
	@Autowired
	private ILaService laservice;
	
	@Autowired
	public void setLaservice(LaServiceImpl laserviceimpl) {
		this.laservice = laserviceimpl;
	}
	
	@Autowired
	private IOverTimeService otservice;
	
	@Autowired
	public void setOtservice(OverTimeServiceImpl otserviceimpl) {
		this.otservice = otserviceimpl;
	}
	
	@Autowired
	private IEmailService emservice;
	
	@Autowired
	public void setEmailService(EmailServiceImpl emserviceimpl) {
		this.emservice = emserviceimpl;
	}
	
	
	
	
	@RequestMapping("")
	public String managerHome(Model model) {
		String name = mservice.getCurrentName();
		model.addAttribute("name", name);
		return ("manager/mHome");
	}
	
	@RequestMapping("/listforapproval")
	public String listforapproval(Model model) {
		model.addAttribute("listforapproval",mservice.findPendingApplications());
		return "/manager/listforapproval";	
	}
	
	@RequestMapping("/listforapprovalcsv")
	public void exportLACSV (HttpServletResponse response) throws Exception {
		
		List<LA> la = mservice.findPendingApplications();
        //set file name and content type
        String filename = "pendingLA.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");
        List<LACsvFile> newlacsv = mservice.LaCsvMapper(la);
        //create a csv writer
        StatefulBeanToCsv<LACsvFile> writer = new StatefulBeanToCsvBuilder<LACsvFile>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false)
                .build();
        //write all users to csv file
        writer.write(newlacsv);
                
    }
	
	//show individual application
	@RequestMapping("/individual/{uid}")
	public String individualapplication(@PathVariable("uid") Integer uid,Model model ) {
		model.addAttribute("la", mservice.findLAByID(uid));
		long fromTime = mservice.getFromTime(uid);
		long toTime = mservice.getToTIme(uid);
		model.addAttribute("leave", mservice.findEmployeesOnLeave(fromTime, toTime));
		return "/manager/individualapplication";
	}

	
	//approve leave, in detailed leave application html after clicking into certain application
	@RequestMapping("/approveapplication/{uid}")
	public String approveApplication(@PathVariable("uid") int uid, Model model) {
		LA la=laservice.getLaById(uid);
		la.setStatus(CommConstants.ApplicationStatus.APPROVED);
		mservice.saveLA(la);
		model.addAttribute("la", la);
		emservice.sendApprovedEmail(uid); // this sends a email to employee when leave is approved
		return "/manager/confirmation";
	}
	

	//reject leave 
	@RequestMapping(value="/rejectapplication",method = RequestMethod.GET)
	public String rejectApplication(String rejectreason,HttpSession session,Model model) {
		LA la=(LA)session.getAttribute("la");
		la.setStatus(CommConstants.ApplicationStatus.REJECTED);
		la.setRejectReason(rejectreason);
		laservice.saveLA(la);
		emservice.sendRejectEmail(la.getUid());
		model.addAttribute("la", la);
		session.removeAttribute("la");
		return "/manager/confirmation";
	}
	
	//key in reject reason
	@RequestMapping("/rejectreasonkeyin/{uid}")
	public String keyInRejectReason(@PathVariable("uid") int uid,Model model,HttpSession session) {
		LA la=laservice.getLaById(uid);
		model.addAttribute("rejectreason", la.getRejectReason());
		session.setAttribute("la", la);
		return "/manager/rejectreason";
	}
	
	//Compensation Claims
	@RequestMapping("/compensationclaims")
	public String compensationclaims(Model model) {
		model.addAttribute("compensationclaims",mservice.findClaims());
		return "/manager/compensationclaims";	
	}
	
	//Approve Claim
	@RequestMapping("/approveClaim/{uid}")
	public String approveClaim(@PathVariable("uid") int uid, Model model) {
		OverTime ot=otservice.getOtById(uid);
		ot.setStatus(CommConstants.ClaimStatus.APPROVED);
		mservice.saveOverTime(ot);
		model.addAttribute("ot", ot);
		return "/manager/claimConfirmation";
	}
		
	//Reject Claim
	@RequestMapping("/rejectClaim/{uid}")
	public String rejectClaim(@PathVariable("uid") int uid, Model model) {
		OverTime ot=otservice.getOtById(uid);
		ot.setStatus(CommConstants.ClaimStatus.REJECTED);
		mservice.saveOverTime(ot);
		model.addAttribute("ot", ot);
		return "/manager/claimConfirmation";
	}
	
	//Employee Leave History
	@RequestMapping("/las/{uid}")
	public String la(@PathVariable("uid") Integer uid, Model model) {
		List<LA> las = laservice.findLaByOwnerId(uid);
		model.addAttribute("lalist", las);
		return "/manager/lalist";
	}
	
	//retrieve employees under logged-in manager 
	@RequestMapping("/employeelist")
	public String getEmployeeListUnderManager(Model model) {
		
		int managerid=mservice.getCurrentUid();
		//find employee under this manager
		ArrayList<User> employeeList= mservice.getEmolyeeList(managerid);
		model.addAttribute("employeelist", employeeList);
		return "/manager/employeelistundermanager";
	}
	
	@RequestMapping("/exportcompensation") 
    public void exportCSV(HttpServletResponse response) throws Exception { 
   
        //set file name and content type 
        String filename = "Compensation.csv"; 

        ArrayList<OverTime> compensationlist=mservice.findClaims(); 
        //for header
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + filename + "\"");
          //create a csv writer 
          StatefulBeanToCsv<OverTimeToCSV> writer = new
          StatefulBeanToCsvBuilder<OverTimeToCSV>(response.getWriter())
          .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
          .withSeparator(CSVWriter.DEFAULT_SEPARATOR) .withOrderedResults(false)
          .build();
  
        writer.write(mservice.convertOverTimetoCSV(compensationlist));

	}	

	//Employees on Leave
	@RequestMapping("/employeeonleave")
	public String employeeonleave(Model model ) {
		long fromTime = TimeUtil.getCurrentTimestamp();
		long toTime = TimeUtil.getCurrentTimestamp();
		model.addAttribute("employeeonleave", mservice.findEmployeesOnLeave(fromTime, toTime));
		return "/manager/employeeonleave";
	}

	@RequestMapping("/exportemployeelist") 
    public void exportEmployeeToCSV(HttpServletResponse response) throws Exception { 
   
        //set file name and content type 
        String filename = "Employee-List.csv"; 
        int managerid=mservice.getCurrentUid();
		//find employee under this manager
		ArrayList<User> employeeList= mservice.getEmolyeeList(managerid);
  
        
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + filename + "\"");
          StatefulBeanToCsv<EmployeeToCSV> writer = new
          StatefulBeanToCsvBuilder<EmployeeToCSV>(response.getWriter())
          .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
          .withSeparator(CSVWriter.DEFAULT_SEPARATOR) .withOrderedResults(false)
          .build();
  
        writer.write(mservice.convertEmployeeListtoCSV(employeeList));

	}
	
	@RequestMapping("/exporteleavehistory/{uid}") 
    public void exportLeaveHistoryToCSV(HttpServletResponse response, @PathVariable("uid") Integer uid) throws Exception { 
        //set file name and content type 
        String filename = "Leave-History.csv"; 
       // String name=mservice.getNameByUid();
		List<LA> leavehistory= laservice.findLaByOwnerId(uid);
  
        //for header
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + filename + "\"");
          //create a csv writer 
          StatefulBeanToCsv<LeaveHistoryToCSV> writer = new
          StatefulBeanToCsvBuilder<LeaveHistoryToCSV>(response.getWriter())
          .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
          .withSeparator(CSVWriter.DEFAULT_SEPARATOR) .withOrderedResults(false)
          .build();
  
        writer.write(mservice.convertLeaveHistorytoCSV(leavehistory));

	}


}






