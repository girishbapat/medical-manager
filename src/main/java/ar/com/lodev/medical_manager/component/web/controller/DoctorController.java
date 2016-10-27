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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ar.com.lodev.medical_manager.component.service.DoctorService;
import ar.com.lodev.medical_manager.component.service.PracticePlaceAssociationService;
import ar.com.lodev.medical_manager.component.web.service.SecurityContextService;
import ar.com.lodev.medical_manager.exception.DoctorPlaceAssociationException;
import ar.com.lodev.medical_manager.exception.PracticePlaceException;
import ar.com.lodev.medical_manager.exception.UserException;
import ar.com.lodev.medical_manager.model.dto.DoctorDTO;
import ar.com.lodev.medical_manager.model.dto.PracticePlaceDTO;

@Controller
@RequestMapping(value="/doctor")
public class DoctorController extends BaseController{
	
	static final Logger logger = Logger.getLogger(DoctorController.class);
	
	@Autowired
	private SecurityContextService securityContextService;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private PracticePlaceAssociationService practicePlaceAssociationService;
	
//	@RequestMapping(value="/practice-places/list" , method=RequestMethod.GET)
//	public String dashboard(Model model){
//		DoctorDTO doctor = doctorService.getFromSession();
//		model.addAttribute("doctor", doctor);
//		model.addAttribute("section", PANEL_SECTION_PRACTICE_PLACES_ASSOCIATED);
//		return "doctor/list-practice-places-associated";
//	}
//	
//	@RequestMapping(value="/json/practice-places/list", method=RequestMethod.GET)
//	public @ResponseBody DataTableData listJson(@RequestParam int start,@RequestParam long draw,
//			@RequestParam(required=false,defaultValue="",value = "search[value]") String practiceId){
//		List<DoctorPracticePlaceAssociationDTO> dtos = practicePlaceAssociationService.search(start);
//		long recordsTotal = practicePlaceAssociationService.countSearch();
//		return new DataTableData(draw,recordsTotal, recordsTotal, dtos);
//	}
	
	@RequestMapping(value="/json/practicePlace/logged" , method=RequestMethod.GET)
	public @ResponseBody PracticePlaceDTO getPracticeLogged(){
		PracticePlaceDTO dto = doctorService.getPracticeLogged();
		return dto;
	}
	
	@RequestMapping(value="/practicePlace/login" , method=RequestMethod.POST)
	public String login(@RequestParam long associationId,Model model){
		try {
			practicePlaceAssociationService.login(associationId);
		} catch (DoctorPlaceAssociationException | PracticePlaceException e) {
			//TODO: redirect with error
			//			model.addAttribute("errorMessage", e.getMessage());
//			model.addAttribute("section", PANEL_SECTION_PRACTICE_PLACES_ASSOCIATED);
//			return "doctor/list-practice-places-associated";
		}
		return "redirect:/practice-place/list";
//		model.addAttribute("section", PANEL_SECTION_PRACTICE_PLACES_ASSOCIATED);
//		return "doctor/list-practice-places-associated";
	}
	
	@RequestMapping(value="/practicePlace/logout" , method=RequestMethod.POST)
	public String logout(){
		practicePlaceAssociationService.logout();
		return "redirect:/practice-place/list";
	}
	
	@RequestMapping(value="/create" , method=RequestMethod.POST)
	public String create(@RequestParam String username,@RequestParam String name,
			@RequestParam String lastname,@RequestParam String password,@RequestParam String email,
			HttpServletRequest request){
		try{
			doctorService.create(username, password, name, lastname,email);
			securityContextService.authenticate(username, password, request);
			return "redirect:../doctor/edit";
		}catch(UserException e){
			//TODO: redirect error
			return "";
		}
	}
	
	
	@RequestMapping(value="/edit" , method=RequestMethod.GET)
	public String editGet(Model model){
		DoctorDTO dto = doctorService.getFromSession();
		model.addAttribute("doctor", dto);
		model.addAttribute("section", PANEL_SECTION_PROFILE);
		return "doctor/profile";
	}
	
	@RequestMapping(value="/edit" , method=RequestMethod.POST)
	public String editPost(@RequestParam String name,@RequestParam String lastname,
			@RequestParam String welcomeMessage,@RequestParam String description,
			@RequestParam(required=false) String password,
			@RequestParam MultipartFile file,Model model) throws IOException{
		DoctorDTO dto = doctorService.edit(name,lastname,welcomeMessage,description,password, file);
		model.addAttribute("doctor", dto);
		model.addAttribute("section", PANEL_SECTION_PROFILE);
		return "doctor/profile";
	}

}
