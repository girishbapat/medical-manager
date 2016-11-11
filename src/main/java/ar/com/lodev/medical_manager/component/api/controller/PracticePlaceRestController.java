package ar.com.lodev.medical_manager.component.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.lodev.medical_manager.component.service.ConfigurationsService;
import ar.com.lodev.medical_manager.component.service.PracticePlaceService;
import ar.com.lodev.medical_manager.model.dto.ConfigurationsDTO;
import ar.com.lodev.medical_manager.model.dto.DoctorDTO;
import ar.com.lodev.medical_manager.model.dto.PracticePlaceDTO;

@RestController
@RequestMapping(value = "/api/1.0/practice-place")
public class PracticePlaceRestController {

	static final Logger logger = Logger.getLogger(MedicalSessionRestController.class);

	@Autowired
	private PracticePlaceService practicePlaceService;

	@Autowired
	private ConfigurationsService configurationsService;

	@RequestMapping(value = "/searchByPracticeId/{practiceId}", method = RequestMethod.GET)
	public List<PracticePlaceDTO> searchByPracticeId(@PathVariable String practiceId) {
		List<PracticePlaceDTO> dtos = practicePlaceService.findByPracticeIdContaining(practiceId);
		return dtos;
	}

	@RequestMapping(value = "/searchDoctorsByPracticeId/{practiceId}", method = RequestMethod.GET)
	public List<DoctorDTO> searchDoctorsByPracticeId(@PathVariable String practiceId) {
		List<PracticePlaceDTO> dtos = practicePlaceService.findByPracticeIdContaining(practiceId.trim());
		List<DoctorDTO> doctors = new ArrayList<DoctorDTO>();
		if (dtos.size() == 1) {
			doctors = practicePlaceService.listDoctorsByPracticeId(dtos.get(0).getId().toString());
		}
		return doctors;
	}

	@RequestMapping(value = "/configurations/{practiceId}", method = RequestMethod.GET)
	public List<ConfigurationsDTO> getConfigurations(@RequestParam(required = false) String key,@PathVariable String practiceId) throws Exception {
		List<ConfigurationsDTO> configDtoList = new ArrayList<ConfigurationsDTO>();

		configDtoList.addAll(configurationsService.findAll());
		List<ConfigurationsDTO> configDtoListFiltered = new ArrayList<ConfigurationsDTO>();
		for(ConfigurationsDTO c : configDtoList){
			if(c.getPracticeId() != null && c.getPracticeId().equalsIgnoreCase(practiceId)){
				configDtoListFiltered.add(c);
			}
		}
		configDtoList = configDtoListFiltered;
	

		return configDtoList;

	}

}
