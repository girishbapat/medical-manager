package ar.com.lodev.medical_manager.component.api.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.lodev.medical_manager.TopicSubscriptionInterceptor;
import ar.com.lodev.medical_manager.component.service.MedicalSessionService;
import ar.com.lodev.medical_manager.exception.MedicalSessionException;
import ar.com.lodev.medical_manager.exception.PracticePlaceException;
import ar.com.lodev.medical_manager.model.dto.MedicalSessionDTO;
import ar.com.lodev.medical_manager.model.dto.SymptomDTO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping(value="/api/1.0/medical-session")
public class MedicalSessionRestController {
	
	static final Logger logger = Logger.getLogger(MedicalSessionRestController.class);
	
	@Autowired
    private SimpMessagingTemplate template;
	@Autowired
	private MedicalSessionService medicalSessionService;
	
	@RequestMapping(value="/create" , method=RequestMethod.POST)
	public MedicalSessionDTO create(@RequestParam String name,@RequestParam String lastname,
			@RequestParam long dateOfBirth,@RequestParam Long practicePlaceId,
			@RequestParam(required=false) String gcmId,@RequestParam(required=false) String email,
			@RequestParam (required=false) Long dateOfAppointment, @RequestParam (required=false) Long doctorId) throws Exception{
		try {
			Date dateOfBirthDate = new Date(dateOfBirth);
			Date appointmentDate = new Date(dateOfAppointment);
			MedicalSessionDTO dto = medicalSessionService.createSession(practicePlaceId, name, 
					lastname, dateOfBirthDate,gcmId,email,appointmentDate,doctorId);
			
			broadCastLogin(dto);
			
			return dto;
		} catch (PracticePlaceException e) {
			throw new PracticePlaceException(e);
		}catch (Exception e) {
			logger.error("ERROR", e);
			throw new Exception(e);
		}
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/update" , method=RequestMethod.POST)
	public ResponseEntity update(@RequestParam long sessionId,@RequestParam String symptoms,
			@RequestParam(required=false) String symptomsRemoved,@RequestParam(required=false) String token) throws Exception{
		Type listType = new TypeToken<ArrayList<SymptomDTO>>() {}.getType();
		List<SymptomDTO> symptomsDto = new Gson().fromJson(symptoms, listType);
		
		List<SymptomDTO> symptomsRemovedDto = new Gson().fromJson(symptomsRemoved, listType);
		
		try {
			medicalSessionService.updateSession(sessionId, symptomsDto,symptomsRemovedDto,token);
			MedicalSessionDTO dto = medicalSessionService.find(sessionId,token);
			broadCastUpdate(dto);
			return ResponseEntity.status(HttpStatus.OK).body(dto);
		} catch (MedicalSessionException e) {
			throw new MedicalSessionException(e);
		} catch (SecurityException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("message:"+e.getMessage());
		}catch (Exception e) {
			logger.error("ERROR", e);
			throw new Exception(e);
		}
	}
	
	private void broadCastUpdate(MedicalSessionDTO session){
		 System.out.println("Fire update");
	     this.template.convertAndSend(TopicSubscriptionInterceptor.MEDICAL_SESSIONS+session.getId(), session);
	}
	
	private void broadCastLogin(MedicalSessionDTO session){
		 System.out.println("Fire login");
	     this.template.convertAndSend(TopicSubscriptionInterceptor.PRACTICE_PLACE_SESSIONS+session.getPracticePlace().getId(), session.getPatient());
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/finish" , method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity finish(@RequestParam long sessionId,@RequestParam(required=false) String token) throws Exception{
		try {
			MedicalSessionDTO dto = medicalSessionService.finish(sessionId,token);
			return ResponseEntity.status(HttpStatus.OK).body(dto);
		} catch (MedicalSessionException e) {
			throw new MedicalSessionException(e);
		} catch (SecurityException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("message:"+e.getMessage());		
		}catch (Exception e) {
			logger.error("ERROR", e);
			throw new Exception(e);
		}
	}
	

}
