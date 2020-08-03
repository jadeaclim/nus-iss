package com.team1.iss.trial.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.team1.iss.trial.repo.UserRepository;
import com.team1.iss.trial.services.interfaces.IEmailService;

@Service
public class EmailServiceImpl implements IEmailService {

	@Autowired
	public JavaMailSender javaMailSender;
	
	@Autowired
	private UserRepository uRepo;
	
	@Override
	public void sendApplicationEmail() {
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String myemail=auth.getName();
		String managerEmail = uRepo.findManageremailbyuseremail(myemail);
		
		if(managerEmail!=null) {
			mailMessage.setTo(managerEmail);
			mailMessage.setSubject("Leave Application Notification" );			
			String text = "There is a new leave application by " + myemail + " for your review. "
					+ "Please login by this link to view: http://localhost:8080/manager/listforapproval ";			
			mailMessage.setText(text);			
			javaMailSender.send(mailMessage);			
		}
	
	}
	@Override
	public void sendRejectEmail(int leaveid) {
		
		SimpleMailMessage rejectMailMessage = new SimpleMailMessage();
		
		String email=uRepo.findemailbyLAUID(leaveid);
		
		rejectMailMessage.setTo(email);
		rejectMailMessage.setSubject("Leave Application Rejected" );
		
		String text = "Your leave application has been rejected.";
		
		rejectMailMessage.setText(text);
		
		javaMailSender.send(rejectMailMessage);
	}

	@Override
	public void sendApprovedEmail(int leaveid) {
		
		SimpleMailMessage ApprovedMailMessage = new SimpleMailMessage();
		
		String email=uRepo.findemailbyLAUID(leaveid);
		ApprovedMailMessage.setTo(email);
		ApprovedMailMessage.setSubject("Leave Application Approved" );
		
		String text = "Your leave application has been approved.";
		
		ApprovedMailMessage.setText(text);
		
		javaMailSender.send(ApprovedMailMessage);
		
	}
	
}
