package ar.com.lodev.medical_manager.component.api.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.com.lodev.medical_manager.component.service.PracticePlaceService;
import ar.com.lodev.medical_manager.model.dto.PracticePlaceDTO;

@RestController
@RequestMapping(value="/api/1.0/practice-place")
public class PracticePlaceRestController {
	
	static final Logger logger = Logger.getLogger(MedicalSessionRestController.class);
	
	@Autowired
	private PracticePlaceService practicePlaceService;
	
	@RequestMapping(value="/searchByPracticeId/{practiceId}" , method=RequestMethod.GET)
	public List<PracticePlaceDTO> searchByPracticeId(@PathVariable String practiceId){
		List<PracticePlaceDTO> dtos = practicePlaceService.findByPracticeIdContaining(practiceId);
		return dtos;
	}

}
