package ar.com.lodev.medical_manager.component.web.controller;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ar.com.lodev.medical_manager.component.service.ConfigurationsService;
import ar.com.lodev.medical_manager.model.Configurations;

@Controller
public class ConfigurationsController extends BaseController {

	@Autowired
	private ConfigurationsService configurationsService;
	@RequestMapping(value="/configurations")
	public @ResponseBody Iterable<Configurations> getConfigurations(@RequestParam(value="key", required=false)String key){
		if(StringUtils.isNotEmpty(StringUtils.trim(key))){
			Configurations configuration = configurationsService.findByKey(StringUtils.trim(key));
			if(configuration!=null){
			return Arrays.asList(configuration);
			}
			return null;
		}
		return configurationsService.findAll();
	}
}
