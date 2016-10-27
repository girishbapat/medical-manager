package ar.com.lodev.medical_manager.component.web.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ar.com.lodev.medical_manager.component.service.UserService;
import ar.com.lodev.medical_manager.exception.UserException;
import ar.com.lodev.medical_manager.ui.model.ValidationResponse;

@Controller
@RequestMapping(value="/user")
public class UserController {
	
	static final Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/forgotPassword",method=RequestMethod.POST)
	public @ResponseBody ResponseEntity forgotPassword(@RequestParam String email){
		try {
			userService.forgotPassword(email);
			return ResponseEntity.status(HttpStatus.OK).body("");
		} catch (UserException e) {
			logger.error("ERROR", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/forgotUsername",method=RequestMethod.POST)
	public @ResponseBody ResponseEntity forgotUsername(@RequestParam String email){
		try {
			userService.forgotUsername(email);;
			return ResponseEntity.status(HttpStatus.OK).body("");
		} catch (UserException e) {
			logger.error("ERROR", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@RequestMapping(value="/usernameUnique",method=RequestMethod.POST)
	public @ResponseBody ValidationResponse usernameUnique(@RequestParam String username){
		boolean valid = userService.usernameUnique(username);
		String message = "";
		if(!valid){
			message = "The username is not available";
		}
		return new ValidationResponse(valid, message);
	}
	
	@RequestMapping(value="/practiceIdUnique",method=RequestMethod.POST)
	public @ResponseBody ValidationResponse practiceIdUnique(@RequestParam String practiceId){
		boolean valid = userService.usernameUnique(practiceId);
		String message = "";
		if(!valid){
			message = "The practice ID is not available";
		}
		return new ValidationResponse(valid, message);
	}
	
	@RequestMapping(value="/emailUnique",method=RequestMethod.POST)
	public @ResponseBody ValidationResponse emailUnique(@RequestParam String email){
		boolean valid = userService.emailUnique(email);
		String message = "";
		if(!valid){
			message = "The email  is not available";
		}
		return new ValidationResponse(valid, message);
	}
	
}
