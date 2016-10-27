package ar.com.lodev.medical_manager.component.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

	@RequestMapping(value="/" , method=RequestMethod.GET)
	public String index(Model model){
		return "index";
	}
	
	@RequestMapping(value="/login" , method=RequestMethod.GET)
	public String login(Model model,@RequestParam(required=false) String error){
		if(error != null && !error.isEmpty()){
			model.addAttribute("error", "Username and password do not match");
		}
		return "login";
	}
	
	@RequestMapping(value="/signup" , method=RequestMethod.GET)
	public String signupGet(Model model,@RequestParam int role){
		String page = "";
		switch(role){
		case 1:
			page = "doctor/signup";
			break;
		case 2:
			page = "practice-admin/signup";
			break;
		}
		return page;
	}
	
	
	
}
