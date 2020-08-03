package com.team1.iss.trial.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;


import com.team1.iss.trial.common.CommConstants;
import com.team1.iss.trial.common.utils.TimeUtil;
import com.team1.iss.trial.domain.Manager;

import com.team1.iss.trial.domain.PublicHoliday;

import com.team1.iss.trial.domain.MassLeaveForm;

import com.team1.iss.trial.domain.User;
import com.team1.iss.trial.domain.FormEditUser;
import com.team1.iss.trial.domain.FormPh;


import com.team1.iss.trial.services.impl.AdminServiceImpl;
import com.team1.iss.trial.services.interfaces.IAdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private IAdminService aservice; // Remember to create the service INTERFACE, not the service class
	
	@Autowired
	public void setIAdminServices(AdminServiceImpl aservicesimpl) { //This is doing a setter DI for services interface
		this.aservice = aservicesimpl; //assigning the services to this instance of service
	}
	
	@RequestMapping("")
	public String adminHome(Model model) {
		
		String name = aservice.getCurrentName();
		model.addAttribute("name", name);
		return ("admin/aHome");
	}
	

	
	@RequestMapping("/list")
	public String list() {
		return "forward:/admin/list/1";
	}	
	@RequestMapping("/list/{page}")
	public String listByPagination(@PathVariable("page") int page, Model model) {
		PageRequest pageable = PageRequest.of(page-1, 3);
		Page<User> userpage = aservice.getPaginatedUsers(pageable);
        int totalPages = userpage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("currentuseremail", auth.getName());
        model.addAttribute("userList", userpage.getContent());
		return "admin/aShowAllUsers";
	}
	
	
	@RequestMapping("/updatemg/{uid}")
	public String updateManager(@PathVariable("uid")int uid, Model model) {
		User u1 = aservice.findUserById(uid);
		List<Manager> m1 = aservice.findAllManager();
        model.addAttribute("u1tohtml",u1);
        model.addAttribute("m1tohtml",m1);
		return "admin/aFormEditUserManager";
	}	
	@RequestMapping("/updatemg")
	public String saveupdatedManager(@ModelAttribute("usertohtml") User user,  Model model) {
		aservice.updateUserManager(user.getManager().getUid(), user.getUid());
		return "redirect:/admin/list";
	}
	
	@RequestMapping("/edit/{uid}")
	public String editForm(@PathVariable("uid")int uid, Model model) {
		FormEditUser uf = aservice.editUser(uid);
		List<String> usertype = new ArrayList();
		usertype.add(CommConstants.UserType.AMDIN);
		usertype.add(CommConstants.UserType.EMPLOYEE);
		usertype.add(CommConstants.UserType.MANAGER);
		model.addAttribute("edituserform", uf);
		model.addAttribute("usertypetohtml", usertype);
		return "admin/aFormEditUsers";
	}
	
	@RequestMapping("/save_edit")
	public String saveEditedUser(@ModelAttribute("edituserform") FormEditUser userform,  Model model) {
		aservice.updateUser(userform);	
		return "redirect:/admin/list";
	}
	
	
	@RequestMapping("/create")
	public String CreateUser(Model model) {
		User u1= new User();
		List<Manager> m1 = aservice.findAllManager(); 
		model.addAttribute("usertohtml",u1); // key value pair (can start using usertohtml.userId, usertohtml.userName in the template view page.)
		model.addAttribute("managertohtml",m1);
		return "admin/acreateuser";
	}
	
	@RequestMapping("/save")
	public String saveUser(@ModelAttribute("usertohtml") User user, Model model) {
		User u = user;
		if (aservice.isEmailAlreadyInUse(u.getEmail())) {
			String errormsg= "User already exist. Please re-enter the email address." ;
			List<Manager> m1 = aservice.findAllManager(); 
			model.addAttribute("managertohtml",m1);
			model.addAttribute("errormsg", errormsg);
			return "admin/acreateuser";
		}
		aservice.saveNewUser(user);
		int newlyaddedUID = aservice.findlatestUID();
		if (user.getManager() != null) {
			aservice.updateUserManager(user.getManager().getUid(), newlyaddedUID);	
		}
		return "redirect:/admin/list";
	}
	
	@RequestMapping("/massEditLeave")
	public String massEditLeave(Model model) {
		MassLeaveForm form = new MassLeaveForm();
		model.addAttribute("mldays", form);
		return "admin/aFormEditleave";
	}

	@RequestMapping("/massSaveLeave")
	public String massSaveLeave(@ModelAttribute("mldays") MassLeaveForm MLForm, Model model) {
		MassLeaveForm a = MLForm;
		
		aservice.updateAllMedicalLeave(MLForm.getMedicalLeaveEntitlement());
		aservice.updateProfAnnualLeave(MLForm.getAnnualLeaveEntitlementProf());
		aservice.updateAdminAnnualLeave(MLForm.getAnnualLeaveEntitlementAdmin());
		
		return "redirect:/admin/list";
	}
	
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String getUser(@RequestParam (value = "word", required = false) String word, Model model) {
		if (word.isEmpty()) {
			return "redirect:/admin/list";
		}
		List<User> users= aservice.getAllUsers(word); //check to name and email
	    model.addAttribute("users", users);
	    return "forward:/admin/list";
	}
	
	@RequestMapping("/ph")
	public String addPh(Model model) {
		List<PublicHoliday> phlist = aservice.getAllPH();
		List<FormPh>phform = new ArrayList();
		for(PublicHoliday p: phlist) {
			FormPh h = new FormPh();
			h.setUid(p.getUid());
			h.setDay(TimeUtil.convertTimestampToyyyMMdd(p.getDay()));
			h.setName(p.getName());
			phform.add(h);
		}	
		model.addAttribute("phform", phform);
		model.addAttribute("phform2", new FormPh());
		return "admin/aFormPubholiday";
	}
	
	//Create public holiday
	@RequestMapping(value = "/saveph", method = RequestMethod.POST)
	public String savePh(@ModelAttribute("phform") FormPh phform) {
		aservice.savePh(phform);
		return "redirect:/admin/ph";
	}
	
	//Retrieve public holiday for editing
	@RequestMapping(value= "/editph/{uid}", method = RequestMethod.GET) 
	public String editPh(Model model, @PathVariable int uid) {
		PublicHoliday ph = aservice.getPh(uid);
		FormPh phf = new FormPh(ph.getUid(), TimeUtil.convertTimestampToyyyMMdd(ph.getDay()), ph.getName());	
		model.addAttribute("phf", phf);
		return "/admin/aEditPubholiday";
	}
	
	//Update public holiday
	@RequestMapping(value = "/updateph", method = RequestMethod.POST)
	public String updatePh(@ModelAttribute("phform") FormPh phform) {	
		aservice.updatePh(phform);
		return "redirect:/admin/ph";
	}
	
	//Delete
	@RequestMapping(value = "/delph/{uid}", method = RequestMethod.GET) 
	public String deletePh(@PathVariable int uid) {
		aservice.deletePh(uid);
		return "redirect:/admin/ph";
	}

}


