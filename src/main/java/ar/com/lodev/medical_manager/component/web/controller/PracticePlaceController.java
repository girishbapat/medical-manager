package ar.com.lodev.medical_manager.component.web.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ar.com.lodev.medical_manager.component.service.DoctorService;
import ar.com.lodev.medical_manager.component.service.PracticePlaceAssociationService;
import ar.com.lodev.medical_manager.component.service.PracticePlaceService;
import ar.com.lodev.medical_manager.model.dto.DoctorDTO;
import ar.com.lodev.medical_manager.model.dto.DoctorPracticePlaceAssociationDTO;
import ar.com.lodev.medical_manager.model.dto.PracticePlaceDTO;
import ar.com.lodev.medical_manager.ui.model.DataTableData;

@Controller
@RequestMapping(value="/practice-place")
public class PracticePlaceController extends BaseController {
	
	static final Logger logger = Logger.getLogger(PracticePlaceController.class);
	
	@Autowired
	private PracticePlaceService practicePlaceService;
	@Autowired
	private PracticePlaceAssociationService practicePlaceAssociationService;
	@Autowired
	private DoctorService doctorService;
	
	@MessageMapping("/broadcast/new-sessions/update")
    @SendTo("/topic/practice-place/sessions")
    public void newSessions(long practicePlaceId) throws Exception {
        return;
    }
	
	@RequestMapping(value="/infoSession" , method=RequestMethod.GET)
	public @ResponseBody PracticePlaceDTO infoSession(){
		PracticePlaceDTO dto = practicePlaceService.getFromAdmin();
		return dto;
	}
	
	@RequestMapping(value="/list" , method=RequestMethod.GET)
	public String dashboard(Model model){
		model.addAttribute("section", PANEL_SECTION_PRACTICE_PLACES);
		DoctorDTO doctor = doctorService.getFromSession();
		model.addAttribute("doctor", doctor);
		return "doctor/list-practice-places";
	}
	
	@RequestMapping(value="/json/list", method=RequestMethod.GET)
	public @ResponseBody DataTableData listJson(@RequestParam int start,@RequestParam long draw,
			@RequestParam(required=false,defaultValue="",value = "search[value]") String practiceId){
		List<DoctorPracticePlaceAssociationDTO> dtos = practicePlaceService.searchForDoctor(practiceId, start);
		long recordsTotal = practicePlaceService.countSearchForDoctor(practiceId);
		return new DataTableData(draw,recordsTotal, recordsTotal, dtos);
	}
	
	@RequestMapping(value="/json/requestAssociaton", method=RequestMethod.POST)
	public @ResponseBody DoctorPracticePlaceAssociationDTO createRequestForAssociation(@RequestParam long practiceId) 
			throws Exception{
		try{
			DoctorPracticePlaceAssociationDTO dto = practicePlaceAssociationService.createRequest(practiceId);
			return dto;
		}catch(Exception e){
			logger.error(ERROR, e);
			throw new Exception(e.getMessage());
		}
	}
	
	@RequestMapping(value="/json/cancelAssociaton", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> deleteRequestForAssociation(@RequestParam long associatonId) 
			throws Exception{
		try{
			practicePlaceAssociationService.deleteRequestFromDoctor(associatonId);
			return new ResponseEntity<String>(HttpStatus.OK);
		}catch(Exception e){
			logger.error(ERROR, e);
			throw new Exception(e.getMessage());
		}
	}

}
