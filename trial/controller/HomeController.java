package com.team1.iss.trial.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	
	@RequestMapping("/")
	public String home(HttpServletRequest request) {
		
		if(request.isUserInRole("MANAGER")){
			return "redirect:/manager";
		}
		
		if(request.isUserInRole("ADMIN")){
			return "redirect:/admin";
		}
		
		if(request.isUserInRole("EMPLOYEE")){
			return "redirect:/employee";
		}
		return"index";
	}

	@RequestMapping("/accessDenied")
	public String denied() {
		return "security/denied";
	}
	
	@RequestMapping("/login")
	public String loginpage() {
		return "security/login";
	}
	
	
	@RequestMapping("/logout-success")
	public String logoutpage() {
		return "security/logout";
	}

}
