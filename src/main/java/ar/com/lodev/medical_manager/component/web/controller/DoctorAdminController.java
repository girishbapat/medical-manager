package ar.com.lodev.medical_manager.component.web.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ar.com.lodev.medical_manager.component.service.ConfigurationsService;
import ar.com.lodev.medical_manager.component.service.PracticePlaceAssociationService;
import ar.com.lodev.medical_manager.component.service.PracticePlaceService;
import ar.com.lodev.medical_manager.model.Configurations;
import ar.com.lodev.medical_manager.model.PracticePlaceDoctorRequestStatus;
import ar.com.lodev.medical_manager.model.dto.ConfigurationsDTO;
import ar.com.lodev.medical_manager.model.dto.DoctorPracticePlaceAssociationDTO;
import ar.com.lodev.medical_manager.model.dto.PracticePlaceDTO;
import ar.com.lodev.medical_manager.ui.model.DataTableData;

@Controller
@RequestMapping(value = "/doctor-admin")
public class DoctorAdminController extends BaseController {

	static final Logger logger = Logger.getLogger(DoctorAdminController.class);

	@Autowired
	private PracticePlaceService practicePlaceService;
	@Autowired
	private PracticePlaceAssociationService practicePlaceAssociationService;

	@Autowired
	private ConfigurationsService configurationsService;

	@RequestMapping(value = "/list-doctors", method = RequestMethod.GET)
	public String dashboard(Model model) {
		model.addAttribute("section", PANEL_SECTION_DOCTORS);
		return "practice-admin/list-doctors";
	}

	@RequestMapping(value = "/list-doctors1", method = RequestMethod.GET)
	public String dashboard1(Model model) {
		model.addAttribute("section", PANEL_SECTION_CONFIGURATIONS);
		return "practice-admin/configurations";
	}

	@RequestMapping(value = "/json/doctor/list", method = RequestMethod.GET)
	public @ResponseBody
	DataTableData listJson(@RequestParam int start, @RequestParam long draw) {
		List<DoctorPracticePlaceAssociationDTO> dtos = practicePlaceService
				.listDoctors(start);
		long recordsTotal = practicePlaceService.countDoctors();
		return new DataTableData(draw, recordsTotal, recordsTotal, dtos);
	}

	@RequestMapping(value = "/json/doctor/list1", method = RequestMethod.GET)
	public @ResponseBody
	DataTableData listJson1(@RequestParam int start, @RequestParam long draw) {
		List<ConfigurationsDTO> configDtoList = configurationsService.findAll();
		PracticePlaceDTO dto = practicePlaceService.getFromAdmin();
		String practiceId = dto.getPracticeId();
		List<ConfigurationsDTO> configDtoFilterList = new ArrayList<ConfigurationsDTO>();
		for(ConfigurationsDTO c : configDtoList){
			if(c.getPracticeId() != null && c.getPracticeId().equalsIgnoreCase(practiceId)){
				configDtoFilterList.add(c);
				
			}
		}
		return new DataTableData(draw, configDtoFilterList.size(),
				configDtoFilterList.size(), configDtoFilterList);

	}

	@RequestMapping(value = "/configurations1", method = RequestMethod.POST)
	public @ResponseBody
	boolean setConfigurations1(@RequestParam String key,
			@RequestParam String value) {

		List<ConfigurationsDTO> configDtoList = new ArrayList<ConfigurationsDTO>();
		if (StringUtils.isNotEmpty(StringUtils.trim(key))) {
			/*ConfigurationsDTO configDTO = configurationsService
					.findByKey(StringUtils.trim(key));*/
			
			configDtoList = configurationsService.findAll();
			PracticePlaceDTO dto = practicePlaceService.getFromAdmin();
			String practiceId = dto.getPracticeId();
			List<ConfigurationsDTO> configDtoFilterList = new ArrayList<ConfigurationsDTO>();
			for(ConfigurationsDTO c : configDtoList){
				if(c.getPracticeId() != null && c.getPracticeId().equalsIgnoreCase(practiceId) && c.getKey().equalsIgnoreCase(key)){
					c.setValue(value);
					configDtoFilterList.add(c);
					
				}
			}
			configDtoList = configDtoFilterList;
			/*ConfigurationsDTO configDTO = new ConfigurationsDTO();
			//configDTO.setId(id);
			if (configDTO != null) {
				configDTO.setValue(value);
				configDtoList.add(configDTO);
			}*/

			List<Configurations> configurations = new ArrayList<Configurations>();
			if (CollectionUtils.isNotEmpty(configDtoList)) {
				for (ConfigurationsDTO confDto : configDtoList) {
					Configurations config = new Configurations(confDto);
					configurations.add(config);
				}
				configurationsService.update(configurations);
				return true;
			}
		}
		return false;
	}

	@RequestMapping(value = "/json/request/updateStatus", method = RequestMethod.POST)
	public @ResponseBody
	DoctorPracticePlaceAssociationDTO updateRequestStatus(
			@RequestParam long associationId, @RequestParam String status)
			throws Exception {
		try {
			PracticePlaceDoctorRequestStatus statusEnum = PracticePlaceDoctorRequestStatus
					.valueOf(status);
			DoctorPracticePlaceAssociationDTO dto = practicePlaceAssociationService
					.updateRequestStatus(associationId, statusEnum);
			return dto;
		} catch (Exception e) {
			logger.error(ERROR, e);
			throw new Exception(e.getMessage());
		}
	}
}
