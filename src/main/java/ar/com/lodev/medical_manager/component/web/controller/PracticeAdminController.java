package ar.com.lodev.medical_manager.component.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ar.com.lodev.medical_manager.component.service.PracticePlaceService;
import ar.com.lodev.medical_manager.component.web.service.SecurityContextService;
import ar.com.lodev.medical_manager.exception.PracticePlaceException;
import ar.com.lodev.medical_manager.exception.UserException;
import ar.com.lodev.medical_manager.model.dto.PracticePlaceDTO;

@Controller
@RequestMapping(value="/practice-admin")
public class PracticeAdminController extends BaseController{
	
	static final Logger logger = Logger.getLogger(PracticeAdminController.class);
	
	@Autowired
	private PracticePlaceService practicePlaceService;
	@Autowired
	private SecurityContextService securityContextService;
	
	
	@RequestMapping(value="/create" , method=RequestMethod.POST)
	public String create(@RequestParam String practiceId,@RequestParam String practiceName,
			@RequestParam String password,@RequestParam String email,
			HttpServletRequest request){
		try{
			practicePlaceService.create(practiceId, practiceId, practiceName, password,email);
			securityContextService.authenticate(practiceId, password, request);
			return "redirect:../doctor-admin/list-doctors";
		}catch(UserException e){
			//TODO: redirect error
			return "";
		}catch(PracticePlaceException e){
			//TODO: redirect error
			return "";
		}
	}
	
	@RequestMapping(value="/edit" , method=RequestMethod.GET)
	public String editGet(Model model){
		PracticePlaceDTO dto = practicePlaceService.getFromAdmin();
		model.addAttribute("practicePlace", dto);
		model.addAttribute("section", PANEL_SECTION_PROFILE);
		return "practice-admin/profile";
	}
	
	@RequestMapping(value="/edit" , method=RequestMethod.POST)
	public String editPost(@RequestParam String description,
			@RequestParam(required=false) String password,
			@RequestParam MultipartFile file,Model model) throws IOException{
		PracticePlaceDTO dto = practicePlaceService.edit(description,password,file);
		model.addAttribute("practicePlace", dto);
		model.addAttribute("section", PANEL_SECTION_PROFILE);
		return "practice-admin/profile";
	}
	
	
}
