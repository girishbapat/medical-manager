package ar.com.lodev.medical_manager.component.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ar.com.lodev.medical_manager.component.service.ConfigurationsService;
import ar.com.lodev.medical_manager.model.Configurations;
import ar.com.lodev.medical_manager.model.dto.ConfigurationsDTO;
import ar.com.lodev.medical_manager.ui.model.DataTableData;

@Controller
public class ConfigurationsController extends BaseController {

	@Autowired
	private ConfigurationsService configurationsService;
	

	@RequestMapping(value = "/configurations", method = RequestMethod.GET)
	public @ResponseBody String getConfigurations(
			@RequestParam(value = "key", required = false) String key,Model model) {
		model.addAttribute("section", PANEL_SECTION_CONFIGURATIONS);
		return "practice-admin/configurations";
		
	}

	@RequestMapping(value = "/configurations", method = RequestMethod.POST)
	public @ResponseBody List<ConfigurationsDTO> setConfigurations(@RequestBody List<ConfigurationsDTO> configurationsDTOs) {
		List<Configurations> configurations=new ArrayList<Configurations>();
		if(CollectionUtils.isNotEmpty(configurationsDTOs)){
			for(ConfigurationsDTO configDTO:configurationsDTOs){
				Configurations config=new Configurations(configDTO);
				configurations.add(config);
			}
			configurationsService.update(configurations);
		}
		return configurationsService.findAll();
	}

	@RequestMapping(value="/json/configuration/list", method=RequestMethod.GET)
	public @ResponseBody DataTableData listJson(
			@RequestParam(required=false) String key
			) throws Exception{
		List<ConfigurationsDTO> configDtoList=new ArrayList<ConfigurationsDTO>();
		if (StringUtils.isNotEmpty(StringUtils.trim(key))) {
			ConfigurationsDTO configDTO = configurationsService.findByKey(StringUtils.trim(key));
			if (configDTO != null) {
				configDtoList.add(configDTO);
			}
		}else{
			configDtoList.addAll(configurationsService.findAll());
		}
		
		return new DataTableData(1,configDtoList.size(), configDtoList.size(), configDtoList);
	}


}
