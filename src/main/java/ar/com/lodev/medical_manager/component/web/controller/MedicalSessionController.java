package ar.com.lodev.medical_manager.component.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ar.com.lodev.medical_manager.component.dao.MedicalSessionRepository;
import ar.com.lodev.medical_manager.component.service.DoctorService;
import ar.com.lodev.medical_manager.component.service.MedicalSessionDoctorAdminService;
import ar.com.lodev.medical_manager.component.service.MedicalSessionService;
import ar.com.lodev.medical_manager.exception.MedicalSessionException;
import ar.com.lodev.medical_manager.exception.PracticePlaceException;
import ar.com.lodev.medical_manager.exception.PracticePlaceLoggedException;
import ar.com.lodev.medical_manager.model.MedicalSession;
import ar.com.lodev.medical_manager.model.dto.DoctorDTO;
import ar.com.lodev.medical_manager.model.dto.MedicalSessionDTO;
import ar.com.lodev.medical_manager.model.dto.SymptomDTO;
import ar.com.lodev.medical_manager.ui.model.DataTableData;
import ar.com.lodev.medical_manager.ui.model.OrderDirection;

@Controller
@RequestMapping(value="/medical-session")
public class MedicalSessionController extends BaseController{
	
	static final Logger logger = Logger.getLogger(PracticeAdminController.class);
	
	@Autowired
	private MedicalSessionDoctorAdminService sessionDoctorAdminService;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private MedicalSessionService medicalSessionService;
	
	@MessageMapping("/broadcast/update")
    @SendTo("/topic/medicalSession")
    public void sessionUpdates(long sessionId) throws Exception {
        return;
    }
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/json/allocate" , method=RequestMethod.POST)
	public ResponseEntity allocate(@RequestParam long sessionId){
		try {
			DoctorDTO doctor = doctorService.getFromSession(); 
			boolean haspermission = doctorService.hasPermissionToListDoctor(doctor.getId(), doctor.getPracticeLogged().getId());
			if(!haspermission){
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Authorized.");
			}
			sessionDoctorAdminService.allocate(sessionId);
			return ResponseEntity.status(HttpStatus.OK).body("");
		} catch (PracticePlaceLoggedException e) {
			logger.error(ERROR, e);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}catch (MedicalSessionException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/json/deallocate" , method=RequestMethod.POST)
	public ResponseEntity deallocate(@RequestParam long sessionId){
		try {
			DoctorDTO doctor = doctorService.getFromSession(); 
			boolean haspermission = doctorService.hasPermissionToListDoctor(doctor.getId(), doctor.getPracticeLogged().getId());
			if(!haspermission){
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Authorized.");
			}
			sessionDoctorAdminService.deallocate(sessionId);
			return ResponseEntity.status(HttpStatus.OK).body("");
		} catch (PracticePlaceLoggedException e) {
			logger.error(ERROR, e);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}catch (MedicalSessionException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
	
	@RequestMapping(value="/doctor/list" , method=RequestMethod.GET)
	public String dashboard(Model model,@RequestParam(required=false)String patienName){
		DoctorDTO doctor = doctorService.getFromSession();
		model.addAttribute("doctor", doctor);
		model.addAttribute("section", PANEL_SECTION_MEDICAL_SESSIONS);
		if(patienName!=null){
			model.addAttribute("patienNameToSearch", patienName);
		}
		return "doctor/list-sessions";
	}
	
	@RequestMapping(value="/practicePlace/list" , method=RequestMethod.GET)
	public String listSessionsForPractice(Model model,@RequestParam(required=false)String patienName){
		model.addAttribute("section", PANEL_SECTION_MEDICAL_SESSIONS);
		if(patienName!=null){
			model.addAttribute("patienNameToSearch", patienName);
		}
		return "practice-admin/list-sessions";
	}
	
	@RequestMapping(value="/json/doctor/list", method=RequestMethod.GET)
	public @ResponseBody DataTableData listJson(@RequestParam int start,@RequestParam long draw,
			@RequestParam(required=false,defaultValue="",value = "search[value]") String patientName,
			@RequestParam(required=false,value = "order[0][column]") Integer columnOrder,
			@RequestParam(required=false,value = "order[0][dir]") String orderDirectionRaw) throws PracticePlaceException{
		DoctorDTO doctor = doctorService.getFromSession(); 
		boolean haspermission = doctorService.hasPermissionToListDoctor(doctor.getId(), doctor.getPracticeLogged().getId());
		if(!haspermission){
			return new DataTableData(draw,0, 0, new ArrayList<MedicalSessionDTO>());
		}
		OrderDirection orderDirection = null;
		String columnToOrder = null;
		if(columnOrder != null && orderDirectionRaw != null){
			orderDirectionRaw = orderDirectionRaw.toUpperCase();
			orderDirection = OrderDirection.valueOf(orderDirectionRaw);
			columnToOrder = getColumnToOrder(columnOrder);
		}
		List<MedicalSessionDTO> dtos = sessionDoctorAdminService.search(patientName, start,
				orderDirection,columnToOrder);
		long recordsTotal = sessionDoctorAdminService.countSearchForDoctor(patientName);
		return new DataTableData(draw,recordsTotal, recordsTotal, dtos);
	}
	
	private String getColumnToOrder(Integer columnOrder){
		String fieldName = null;
		switch (columnOrder) {
		case 0:
			fieldName = "open";
			break;
		case 1:
			fieldName = "startDate";
			break;
		case 2:
			fieldName = "patient.name";
			break;
		case 3:
			fieldName = "patient.dateOfBirth";
			break;
		case 4:
			fieldName = "availableToBeTaken";
			break;
		}
		return fieldName;
	}
	
	@RequestMapping(value="/{sessionId}", method=RequestMethod.GET)
	public @ResponseBody MedicalSessionDTO findForDoctor(@PathVariable long sessionId) throws PracticePlaceLoggedException{
		MedicalSessionDTO dto = sessionDoctorAdminService.find(sessionId);
		return dto;
	}
	
	@RequestMapping(value="/json/practicePlace/list", method=RequestMethod.GET)
	public @ResponseBody DataTableData listJsonForPractice(@RequestParam int start,@RequestParam long draw,
			@RequestParam(required=false,defaultValue="",value = "search[value]") String patientName,
			@RequestParam(required=false,value = "order[0][column]") Integer columnOrder,
			@RequestParam(required=false,value = "order[0][dir]") String orderDirectionRaw) throws PracticePlaceException{
		OrderDirection orderDirection = null;
		String columnToOrder = null;
		if(columnOrder != null && orderDirectionRaw != null){
			orderDirectionRaw = orderDirectionRaw.toUpperCase();
			orderDirection = OrderDirection.valueOf(orderDirectionRaw);
			columnToOrder = getColumnToOrder(columnOrder);
		}
		List<MedicalSessionDTO> dtos = medicalSessionService.searchForPractice(patientName, start,
				orderDirection,columnToOrder);
		long recordsTotal = medicalSessionService.countSearchForPractice(patientName);
		return new DataTableData(draw,recordsTotal, recordsTotal, dtos);
	}
	
	
	@RequestMapping(value="/json/doctor/{sessionId}/symptoms", method=RequestMethod.GET)
	public @ResponseBody List<SymptomDTO> listSymptoms(@PathVariable long sessionId) throws PracticePlaceException{
		List<SymptomDTO> dtos = sessionDoctorAdminService.listSymptoms(sessionId);
		return dtos;
	}
	
	@RequestMapping(value="/json/practicePlace/{sessionId}/symptoms", method=RequestMethod.GET)
	public @ResponseBody List<SymptomDTO> listSymptomsForPractice(@PathVariable long sessionId){
		List<SymptomDTO> dtos = medicalSessionService.listSymptoms(sessionId);
		return dtos;
	}
}
