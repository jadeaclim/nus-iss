package com.team1.iss.trial.services.interfaces;


public interface IEmailService {
	
	public void sendApplicationEmail();
	
	public void sendRejectEmail(int leaveid);
	
	public void sendApprovedEmail(int leaveid);
	

}
