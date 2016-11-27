package ar.com.lodev.medical_manager.component.web.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.lodev.medical_manager.component.service.ChatMedicalSessionService;
import ar.com.lodev.medical_manager.component.service.DoctorService;
import ar.com.lodev.medical_manager.model.dto.ChatMedicalSessionDTO;
import ar.com.lodev.medical_manager.model.dto.DoctorDTO;

@RestController
@RequestMapping(value="/chat-medicalSession")
public class ChatMedicalSessionController extends BaseController{
	
	static final Logger logger = Logger.getLogger(ChatMedicalSessionController.class);
	
	@Autowired
	private ChatMedicalSessionService chatMedicalSessionService;
	
	@Autowired
	private DoctorService doctorService;
	
	
	@RequestMapping(value="/doctor/create" , method=RequestMethod.POST)
	public void createMessageFromDoctor(@RequestParam String message,@RequestParam long chatSessionId) throws Exception{
		try{
			DoctorDTO doctor = doctorService.getFromSession(); 
			boolean haspermission = doctorService.hasPermissionToListDoctor(doctor.getId(), doctor.getPracticeLogged().getId());
			if(haspermission){
				chatMedicalSessionService.createMessageFromPractice(chatSessionId, message);
			}else{
				throw new SecurityException("Not Authorized.");
			}
		}catch(SecurityException e){
			logger.error(ERROR, e);
			throw new SecurityException(e);
		}catch (Exception e) {
			logger.error(ERROR, e);
			throw new Exception(ERROR);
		}
	}
	
	@RequestMapping(value="/practicePlace/create" , method=RequestMethod.POST)
	public void createMessageFromPractice(@RequestParam String message,@RequestParam long chatSessionId) throws Exception{
		try{
			chatMedicalSessionService.createMessageFromPractice(chatSessionId, message);
		}catch(SecurityException e){
			logger.error(ERROR, e);
			throw new SecurityException(e);
		}catch (Exception e) {
			logger.error(ERROR, e);
			throw new Exception(ERROR);
		}
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/doctor/{sessionId}" , method=RequestMethod.GET)
	public ResponseEntity listMessages(@PathVariable long sessionId) throws Exception{
		try{
			DoctorDTO doctor = doctorService.getFromSession();
			boolean haspermission = doctorService.hasPermissionToListDoctor(doctor.getId(), doctor.getPracticeLogged().getId());
			if(!haspermission){
				throw new SecurityException("Not Authorized.");
			}
			ChatMedicalSessionDTO dto = chatMedicalSessionService.findChatFromDoctor(sessionId);
			return ResponseEntity.status(HttpStatus.OK).body(dto);
		}catch(SecurityException e){
			logger.error(ERROR, e);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}catch (Exception e) {
			logger.error(ERROR, e);
			throw new Exception(ERROR);
		}
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/practicePlace/{sessionId}" , method=RequestMethod.GET)
	public ResponseEntity listMessagesForPractice(@PathVariable long sessionId) throws Exception{
		try{
			DoctorDTO doctor = doctorService.getFromSession(); 
			boolean haspermission = doctorService.hasPermissionToListDoctor(doctor.getId(), doctor.getPracticeLogged().getId());
			if(!haspermission){
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Authorized.");
			}
			ChatMedicalSessionDTO dto = chatMedicalSessionService.findChatFromPractice(sessionId);
			return ResponseEntity.status(HttpStatus.OK).body(dto);
		}catch(SecurityException e){
			logger.error(ERROR, e);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}catch (Exception e) {
			logger.error(ERROR, e);
			throw new Exception(ERROR);
		}
	}
	
}
